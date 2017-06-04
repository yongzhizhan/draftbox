package cn.zhanyongzhi.draftbox.pratice;

import cn.zhanyongzhi.draftbox.pratice.exception.CanNotWriteMoreObjectException;

import java.io.Closeable;
import java.io.IOException;

interface IObjectFileStream<T extends ISerialize> extends Closeable {
    T readObject() throws IOException, IllegalAccessException, InstantiationException;

    T readObject(int index) throws IOException, IllegalAccessException, InstantiationException;

    int writeObject(ISerialize object) throws IOException, CanNotWriteMoreObjectException;

    void deleteObject(int index) throws IOException;

    void flush() throws IOException;
}
