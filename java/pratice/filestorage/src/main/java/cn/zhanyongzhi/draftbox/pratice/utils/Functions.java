package cn.zhanyongzhi.draftbox.pratice.utils;

import java.nio.ByteBuffer;

public class Functions {
    public static long Size1K = 1024;
    public static long Size1M = 1024 * 1024;
    public static long Size1G = 1024 * 1024 * 1024;


    public static int byteArrayToInt(byte[] b){
        return byteArrayToInt(b, 0);
    }

    public static int byteArrayToInt(byte[] b, int offset) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put(b, offset, 4);
        buffer.flip();

        return buffer.getInt();
    }

    public static byte[] intToByteArray(int a) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(a);
        return buffer.array();
    }

    public static long byteArrayToLong(byte[] b){
        return byteArrayToLong(b, 0);
    }

    public static long byteArrayToLong(byte[] b, int offset){
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(b, offset, 8);
        buffer.flip();

        return buffer.getLong();
    }

    public static byte[] longToByteArray(long a){
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(a);
        return buffer.array();
    }

    public static boolean isEquals(byte[] src, byte[] dest, int len){
        int minLen = Math.min(src.length, dest.length);
        if(minLen < len)
            len = minLen;

        for(int i=0; i<len; i++){
            if(src[i] != dest[i])
                return false;
        }

        return true;
    }

    public static long getUnsignedInt(int val){
        return val & 0x0FFFFFFFFL;
    }
}
