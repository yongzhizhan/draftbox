package cn.zhanyongzhi.draftbox.pratice;

import cn.zhanyongzhi.draftbox.pratice.exception.CanNotWriteMoreObjectException;
import cn.zhanyongzhi.draftbox.pratice.exception.FileRemainSizeLessThanZeroException;
import cn.zhanyongzhi.draftbox.pratice.utils.Config;
import cn.zhanyongzhi.draftbox.pratice.utils.Functions;
import org.apache.log4j.Logger;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class FileStorage<T extends ISerialize> implements IFileStorage<T>, Closeable{
    private static Logger logger = Logger.getLogger(FileStorage.class);

    private static final String COMPACT_FILE_SUFFIX = "_compacting";
    private static final long MAX_FILE_SIZE = Functions.Size1G * 64;

    private IObjectFileStream objectFileStream;
    private File curFile;
    private File compactFile;
    private Class<T> clazz;
    private boolean isCompacting;
    private boolean isReadOnly;

    private Map<Long, Integer> blockIdMap = new HashMap<>();

    private Config config = Config.getInstance();

    FileStorage(File file, Class<T> clazz) throws IOException, FileRemainSizeLessThanZeroException {
        this.curFile = file;
        this.clazz = clazz;

        init();
    }

    private void init() throws IOException, FileRemainSizeLessThanZeroException {
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

        objectFileStream = new ObjectFileStream<>(curFile, clazz, MAX_FILE_SIZE);
    }

    private void initBlockMap() throws IOException, FileRemainSizeLessThanZeroException {
        blockIdMap.clear();

        Iterator<T> iterator = getIterator();
        while(iterator.hasNext()){
            T obj = iterator.next();
            Long key = obj.getKey();
            Integer index = obj.getIndex();

            blockIdMap.put(key, index);
        }
    }

    @Override
    public Iterator<T> getIterator() throws IOException, FileRemainSizeLessThanZeroException {
        updateCurFile();

        @SuppressWarnings("unchecked")
        IObjectFileStream<T> stream = new ObjectFileStream<>(curFile, clazz, MAX_FILE_SIZE);
        return new FileStorageIterator<>(stream);
    }

    @Override
    public long append(T object) throws IOException, CanNotWriteMoreObjectException {
        try {
            int index = getObjectFileStream().writeObject(object);
            long key = object.getKey();

            blockIdMap.put(key, index);

            return key;
        }catch (IOException e){
            logger.error("append faild.", e);
            markReadOnly();

            throw e;
        }
    }

    @Override
    public void delete(long key) throws IOException {
        try {
            Integer index = blockIdMap.get(key);
            if(null == index)
                return;

            getObjectFileStream().deleteObject(index);
        }catch (IOException e){
            logger.error(String.format("delete key %d failed", key), e);
            markReadOnly();

            throw e;
        }
    }

    @Override
    public T getObject(long key) throws IOException, InstantiationException, IllegalAccessException {
        try {
            Integer index = blockIdMap.get(key);
            if(null == index)
                return null;

            //noinspection unchecked
            return (T) getObjectFileStream().readObject(index);
        }catch (Exception e){
            logger.error(String.format("get object %d failed", key), e);
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

                    compactor = new Compactor(self, newFile, clazz, MAX_FILE_SIZE);
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
    private void updateCurFile() throws IOException{
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

        try {
            objectFileStream = new ObjectFileStream<>(curFile, clazz, MAX_FILE_SIZE);
            //重新初始化块映射
            initBlockMap();
        } catch (FileRemainSizeLessThanZeroException ignore) {
        }
    }
}
