package cn.zhanyongzhi.draftbox.pratice;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class Compactor<T extends ISerialize> implements ICompactor {
    private IFileStorage fileStorage;
    private File newFile;
    private Class clazz;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    Compactor(IFileStorage fileStorage, File newFile, Class clazz) throws IOException {
        this.fileStorage = fileStorage;

        if(newFile.exists())
           newFile.delete();

        newFile.createNewFile();
        this.newFile = newFile;
    }

    @SuppressWarnings("unchecked")
    public File doCompact() throws IOException{
        try(IObjectFileStream objectFileStream = new ObjectFileStream<>(newFile, clazz)){
            Iterator<T> iterator = fileStorage.getIterator();
            while(iterator.hasNext()){
                T obj = iterator.next();
                byte flag = obj.getFlag();

                if(ISerialize.FLAG_DELETE == flag){
                    continue;
                }

                objectFileStream.writeObject(obj);
            }
        }catch (Exception e){
            return null;
        }

        return newFile;
    }
}
