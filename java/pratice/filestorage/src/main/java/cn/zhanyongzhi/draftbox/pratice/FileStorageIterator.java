package cn.zhanyongzhi.draftbox.pratice;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Iterator;

class FileStorageIterator<T extends ISerialize> implements Iterator<T>{
    private static Logger logger = Logger.getLogger(FileStorageIterator.class);

    private IObjectFileStream stream;
    private T nextObj;

    FileStorageIterator(IObjectFileStream<T> stream) {
       this.stream = stream;
    }

    @Override
    public boolean hasNext() {
        if(null == nextObj)
            nextObj = readObject();

        return null != nextObj;
    }

    @Override
    public T next() {
        if(null == nextObj)
            nextObj = readObject();

        T retObj = nextObj;
        nextObj = null;

        return retObj;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private T readObject() {
        try {
            //noinspection unchecked
            nextObj = (T) stream.readObject();
            return nextObj;
        } catch (IOException e) {
            logger.error("file error", e);
        } catch (IllegalAccessException e) {
            logger.error("not have access permission", e);
        } catch (InstantiationException e) {
            logger.error("instance object failed.", e);
        }

        return null;
    }
}
