package cn.zhanyongzhi.draftbox.pratice;

import java.io.Closeable;
import java.io.IOException;

interface IObjectFileStream<T extends ISerialize> extends Closeable {
    T readObject() throws IOException, IllegalAccessException, InstantiationException;

    T readObject(int index) throws IOException, IllegalAccessException, InstantiationException;

    int writeObject(ISerialize object) throws IOException;

    void deleteObject(int index) throws IOException;

    void flush() throws IOException;
}
