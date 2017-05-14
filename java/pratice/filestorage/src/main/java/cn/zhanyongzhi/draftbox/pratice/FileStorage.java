package cn.zhanyongzhi.draftbox.pratice;

import cn.zhanyongzhi.draftbox.pratice.utils.Config;
import org.apache.log4j.Logger;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

class FileStorage<T extends ISerialize> implements IFileStorage<T>, Closeable{
    private static Logger logger = Logger.getLogger(FileStorage.class);

    private static final String COMPACT_FILE_SUFFIX = "_compacting";

    private IObjectFileStream objectFileStream;
    private File curFile;
    private File compactFile;
    private Class<T> clazz;
    private boolean isCompacting;
    private boolean isReadOnly;

    private Config config = Config.getInstance();

    FileStorage(File file, Class<T> clazz) throws IOException {
        this.curFile = file;
        this.clazz = clazz;

        init();
    }

    private void init() throws IOException {
        isReadOnly = config.getValue(Config.IS_READ_ONLY_KEY, Config.IS_READ_ONLY_VAL);

        //check compact file
        String compactFilePath = curFile.getPath() + COMPACT_FILE_SUFFIX;
        File newCompactFile = new File(compactFilePath);

        //compact一半的情况
        if(curFile.exists() && newCompactFile.exists()){
            newCompactFile.delete();
        }

        //修复压缩没及时移动的问题
        if(!curFile.exists() && newCompactFile.exists()){
            newCompactFile.renameTo(curFile);
        }

        //not have any file, create and clean data
        //TODO:有磁盘用来测试时的时候,再清理数据
        if(!curFile.exists()){
            curFile.createNewFile();
        }

        objectFileStream = new ObjectFileStream<>(curFile, clazz);
    }

    @Override
    public Iterator<T> getIterator() throws IOException {
        updateCurFile();

        @SuppressWarnings("unchecked")
        IObjectFileStream<T> stream = new ObjectFileStream<>(curFile, clazz);
        return new FileStorageIterator<>(stream);
    }

    @Override
    public int append(T object) throws IOException {
        try {
            return getObjectFileStream().writeObject(object);
        }catch (IOException e){
            logger.error("append faild.", e);
            markReadOnly();

            throw e;
        }
    }

    @Override
    public void delete(int index) throws IOException {
        try {
            getObjectFileStream().deleteObject(index);
        }catch (IOException e){
            logger.error(String.format("delete index %d failed", index), e);
            markReadOnly();

            throw e;
        }
    }

    @Override
    public T getObject(int index) throws IOException, InstantiationException, IllegalAccessException {
        try {
            //noinspection unchecked
            return (T) getObjectFileStream().readObject(index);
        }catch (Exception e){
            logger.error(String.format("get object %d failed", index), e);
            markReadOnly();

            throw e;
        }
    }

    @Override
    public void flush() throws IOException {
        try {
            getObjectFileStream().flush();
        }catch (IOException e){
            logger.error("flush failed", e);
            markReadOnly();

            throw e;
        }
    }

    @Override
    public void compact(){
        if(isReadOnly()){
            logger.warn("read only file can not compact.");
            return;
        }

        if(isCompacting()){
            logger.warn("is compacting, can not start a new compact");
            return;
        }

        isCompacting = true;
        final FileStorage self = this;

        new Thread(new Runnable() {
            @Override
            public void run() {
                Compactor compactor;
                try {
                    String compactFilePath = curFile.getPath() + COMPACT_FILE_SUFFIX;
                    File newFile = new File(compactFilePath);

                    compactor = new Compactor(self, newFile, clazz);
                    compactFile = compactor.doCompact();
                } catch (IOException e) {
                    logger.error("compact failed.", e);
                } finally {
                    isCompacting = false;
                }
            }
        }).run();
    }

    @Override
    public boolean isReadOnly() {
        return isReadOnly;
    }

    @Override
    public boolean isCompacting() {
        return isCompacting;
    }

    @Override
    public void close() throws IOException {
        getObjectFileStream().close();
    }

    private void markReadOnly(){
        //write config file
        isReadOnly = true;

        config.setValue(Config.IS_READ_ONLY_KEY, "true");
    }

    private IObjectFileStream getObjectFileStream() throws IOException {
        if(isCompacting())
            return objectFileStream;

        if(null == compactFile)
            return objectFileStream;

        updateCurFile();
        return objectFileStream;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void updateCurFile() throws IOException {
        if(isCompacting())
            return;

        if(null == compactFile)
            return;

        //handle compact
        objectFileStream.close();

        //delete之后失败,会出现临时状态,初始化时,可以恢复
        curFile.delete();
        compactFile.renameTo(curFile);
        compactFile = null;

        objectFileStream = new ObjectFileStream<>(curFile, clazz);
    }
}
