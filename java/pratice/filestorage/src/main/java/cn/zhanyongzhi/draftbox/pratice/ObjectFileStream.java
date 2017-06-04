package cn.zhanyongzhi.draftbox.pratice;

import cn.zhanyongzhi.draftbox.pratice.exception.CanNotWriteMoreObjectException;
import cn.zhanyongzhi.draftbox.pratice.exception.FileRemainSizeLessThanZeroException;
import cn.zhanyongzhi.draftbox.pratice.utils.Functions;
import com.sun.media.sound.InvalidDataException;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.channels.Channels;
import java.util.Arrays;
import java.util.zip.CRC32;

class ObjectFileStream<T> implements IObjectFileStream {
    private static Logger logger = Logger.getLogger(ObjectFileStream.class);

    private static final byte[] MAGIC_NUM = "GMFS".getBytes();
    private static final int PACK_SIZE = 8;

    private FileInputStream in;
    private FileOutputStream out;
    private RandomAccessFile randomAccessFile;

    private long curReadIndex = 0;
    private long curWriteIndex = 0;

    private long remainFileSize;

    private Class<T> clazz;

    ObjectFileStream(File file, Class<T> objClass, long maxFileSize) throws FileNotFoundException, FileRemainSizeLessThanZeroException {
        in = new FileInputStream(file);
        out = new FileOutputStream(file, true);

        randomAccessFile = new RandomAccessFile(file, "rw");

        long fileSize = file.length();
        curWriteIndex = getCurrentIndex(fileSize);

        remainFileSize = maxFileSize - fileSize;
        if(0 > remainFileSize){
            logger.error("file remain size less than zero");
            throw new FileRemainSizeLessThanZeroException();
        }

        clazz = objClass;
    }

    @Override
    public ISerialize readObject() throws IOException, IllegalAccessException, InstantiationException {
        return readObject(in);
    }

    @Override
    public ISerialize readObject(int index) throws IOException, IllegalAccessException, InstantiationException {
        long offset = getOffset(index);

        if(offset >= randomAccessFile.length()){
            throw new EOFException("offset is:" + offset);
        }

        //使用随机读操作
        randomAccessFile.seek(offset);
        InputStream inputStream = Channels.newInputStream(randomAccessFile.getChannel());

        return readObject(inputStream);
    }

    private ISerialize readObject(InputStream in) throws IllegalAccessException, IOException, InstantiationException {
        byte[] data = new byte[MAGIC_NUM.length];
        IOUtils.readFully(in, data);

        if(!Arrays.equals(data, MAGIC_NUM)){
            throw new IllegalAccessException("magic num not equal.");
        }

        ISerialize serialize = (ISerialize) clazz.newInstance();

        byte[] keyByte = new byte[8];
        IOUtils.readFully(in, keyByte);

        long key = Functions.byteArrayToLong(keyByte);
        serialize.setKey(key);

        byte[] flag = new byte[1];
        IOUtils.readFully(in, flag);

        serialize.setFlag(flag[0]);

        byte[] dataLenBytes = new byte[4];
        IOUtils.readFully(in, dataLenBytes);

        int dataLen = Functions.byteArrayToInt(dataLenBytes);
        if(-1 == dataLen)
            return null;

        data = serialize.readObject(in, dataLen);
        if(null == data)
            throw new InstantiationException();

        byte[] checkSumByte = new byte[8];
        IOUtils.readFully(in, checkSumByte, 0, checkSumByte.length);

        long checkSum = Functions.byteArrayToLong(checkSumByte);

        CRC32 crc32 = new CRC32();
        crc32.update(data);

        long curCheckSum = crc32.getValue();
        if(curCheckSum != checkSum)
            throw new InvalidDataException();

        //1 is flag mark
        int appendSize = MAGIC_NUM.length + keyByte.length + 1 + dataLenBytes.length + dataLen + checkSumByte.length;
        int packSize = getPackSize(appendSize);
        if(0 != packSize) {
            //noinspection ResultOfMethodCallIgnored
            in.skip(packSize);
        }

        int objIndex = (int)curReadIndex;
        serialize.setIndex(objIndex);

        //inc the read index
        curReadIndex += appendSize / PACK_SIZE;

        return serialize.getObject();
    }

    @Override
    public int writeObject(ISerialize object) throws IOException, CanNotWriteMoreObjectException {
        IOUtils.write(MAGIC_NUM, out);

        //写入key
        long key = object.getKey();

        byte[] keyBytes = Functions.longToByteArray(key);
        IOUtils.write(keyBytes, out);

        //写入flag
        IOUtils.write(new byte[]{ISerialize.FLAG_NORMAL}, out);

        //写入文件长度
        int objSize = object.getObjectSize();
        byte[] objSizeByte = Functions.intToByteArray(objSize);

        IOUtils.write(objSizeByte, out);

        byte[] data;

        //写入内容
        if(0 != objSize) {
            data = object.writeObject(out, (int) curWriteIndex);
        }else{
            data = new byte[0];
        }

        //写入checksum
        CRC32 crc32 = new CRC32();
        crc32.update(data);

        byte[] crc32Byte = Functions.longToByteArray(crc32.getValue());
        out.write(crc32Byte);

        //write pack size, 1 == flag size
        int appendSize = MAGIC_NUM.length + keyBytes.length + 1 + objSizeByte.length + objSize + crc32Byte.length;
        int packSize = getPackSize(appendSize);

        if(0 != packSize) {
            byte[] skipByte = new byte[packSize];
            out.write(skipByte);
        }

        int writedSize = appendSize + packSize;
        long curRemainFileSize = remainFileSize - writedSize;
        if(curRemainFileSize < 0)
            throw new CanNotWriteMoreObjectException();

        remainFileSize = curRemainFileSize;

        int objIndex = (int) curWriteIndex;

        //更新索引,对齐之后,最少有一个了
        curWriteIndex += writedSize / PACK_SIZE;

        out.flush();

        return objIndex;
    }

    @Override
    public void flush() throws IOException {
        out.getChannel().force(true);
    }

    @Override
    public void deleteObject(int index) throws IOException {
        //magic num + block id length
        long offset = getOffset(index) + MAGIC_NUM.length + 8;
        randomAccessFile.seek(offset);

        //将文件长度，设置为-1，表示删除
        randomAccessFile.write(new byte[]{ISerialize.FLAG_DELETE});
        randomAccessFile.getChannel().force(true);
    }

    @Override
    public void close() throws IOException {
        in.close();
        out.close();
        randomAccessFile.close();
    }

    private long getOffset(int index){
        return Functions.getUnsignedInt(index) * PACK_SIZE;
    }

    private long getCurrentIndex(long fileSize){
        return fileSize / PACK_SIZE;
    }

    private int getPackSize(int size){
        return PACK_SIZE * ((size + PACK_SIZE - 1) / PACK_SIZE ) - size;
    }
}
