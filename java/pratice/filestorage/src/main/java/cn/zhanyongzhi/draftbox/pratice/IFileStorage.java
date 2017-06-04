package cn.zhanyongzhi.draftbox.pratice;

import cn.zhanyongzhi.draftbox.pratice.exception.CanNotWriteMoreObjectException;
import cn.zhanyongzhi.draftbox.pratice.exception.FileRemainSizeLessThanZeroException;

import java.io.IOException;
import java.util.Iterator;

interface IFileStorage<T> {
    Iterator getIterator() throws IOException, FileRemainSizeLessThanZeroException;

    long append(T object) throws IOException, CanNotWriteMoreObjectException;

    void delete(long key) throws IOException;

    T getObject(long key) throws IOException, InstantiationException, IllegalAccessException;

    void flush() throws IOException;

    void compact() throws IOException;

    boolean isReadOnly();

    boolean isCompacting();
}
