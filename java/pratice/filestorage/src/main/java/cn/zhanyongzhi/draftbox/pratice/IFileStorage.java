package cn.zhanyongzhi.draftbox.pratice;

import java.io.IOException;
import java.util.Iterator;

interface IFileStorage<T> {
    Iterator getIterator() throws IOException;

    int append(T object) throws IOException;

    void delete(int index) throws IOException;

    T getObject(int index) throws IOException, InstantiationException, IllegalAccessException;

    void flush() throws IOException;

    void compact() throws IOException;

    boolean isReadOnly();

    boolean isCompacting();
}
