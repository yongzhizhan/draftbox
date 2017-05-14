package cn.zhanyongzhi.draftbox.pratice;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

interface ISerialize<T> {
    byte FLAG_NORMAL = 0x0;
    byte FLAG_DELETE = 0x1;

    void setFlag(byte flag);

    byte getFlag();

    int getObjectSize();

    byte[] writeObject(OutputStream out, int index) throws IOException;

    byte[] readObject(InputStream in, int dataLen) throws IOException;

    ISerialize<T> getObject();
}
