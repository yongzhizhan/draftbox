package cn.zhanyongzhi.draftbox.pratice;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StorageData implements ISerialize {
    private byte[] data;
    private byte flag;
    private Long key;
    private Integer index;

    public void setData(byte[] data){
        this.data = data;
    }

    public byte[] getData(){
        return data;
    }

    public boolean isDelete(){
        return FLAG_DELETE == flag;
    }

    @Override
    public void setIndex(Integer index){
        this.index = index;
    }

    @Override
    public Integer getIndex() {
        return index;
    }

    @Override
    public long getKey() {
        if(null == key)
            key = KeyGenerate.getInstance().nextId();

        return key;
    }

    @Override
    public void setKey(long key) {
        this.key = key;
    }

    @Override
    public void setFlag(byte flag) {
        this.flag = flag;
    }

    @Override
    public byte getFlag() {
        return flag;
    }

    @Override
    public int getObjectSize() {
        return data.length;
    }

    @Override
    public byte[] writeObject(OutputStream out, int index) throws IOException {
        out.write(data);
        return data;
    }

    @Override
    public byte[] readObject(InputStream in, int dataLen) throws IOException {
        data = new byte[dataLen];

        //noinspection ResultOfMethodCallIgnored
        IOUtils.readFully(in, data);
        return data;
    }

    @Override
    public ISerialize getObject() {
        return this;
    }
}