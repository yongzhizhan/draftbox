package cn.zhanyongzhi.draftbox.pratice;

import cn.zhanyongzhi.draftbox.pratice.utils.Functions;
import org.junit.Assert;
import org.junit.Test;

import java.util.zip.CRC32;

public class EmptyCrcTest {
    @Test
    public void testEmptyCrc32(){
        CRC32 crc32 = new CRC32();

        crc32.update(new byte[0]);
        long val = crc32.getValue();
        Assert.assertNotNull(val);
    }
}
