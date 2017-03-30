package cn.zhanyongzhi.validationidea;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MMapTest {
    String filePath = "mappedFile.txt";

    @Before
    public void setUp() throws IOException {
        new File(filePath).delete();
    }

    @Test
    public void testDefault() throws IOException {
        RandomAccessFile mappedFile = new RandomAccessFile(filePath, "rw");
        MappedByteBuffer buffer = mappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 1024 * 10);

        for(int i = 0; i<100; i++){
            buffer.put("test".getBytes());
        }

        mappedFile.close();


        FileInputStream inputStream = new FileInputStream(new File(filePath));
        byte[] data = new byte[1024];

        IOUtils.readFully(inputStream, data);

        String str = new String(data);

        Assert.assertTrue(str.startsWith("test"));
    }


    @Test
    public void testAppend() throws IOException {
        File destFile = new File(filePath);

        OutputStream out = new FileOutputStream(destFile, true);
        out.write("1111111".getBytes());

        RandomAccessFile mappedFile = new RandomAccessFile(filePath, "r");
        MappedByteBuffer buffer = mappedFile.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, destFile.length());

        out.write("test".getBytes());

        byte[] data = new byte[1024];
        buffer.get(data, 0, 1024);

        String str = new String(data);
        Assert.assertTrue(str.endsWith("test"));

        mappedFile.close();
        out.close();
    }
}
