package cn.zhanyongzhi.draftbox.pratice;

import cn.zhanyongzhi.draftbox.pratice.exception.CanNotWriteMoreObjectException;
import cn.zhanyongzhi.draftbox.pratice.exception.FileRemainSizeLessThanZeroException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class FileStorageIteratorTest {
    File testFile;

    @SuppressWarnings({"ResultOfMethodCallIgnored", "ConstantConditions"})
    @BeforeMethod
    public void setup() throws IOException {
        String pathName = getClass().getClassLoader().getResource("").getFile() + "/foo.data";
        testFile = new File(pathName);

        if(testFile.exists())
            testFile.delete();

        testFile.createNewFile();
    }

    @DataProvider(name = "seq_read_num")
    Object[][] getSeqReadNum(){
        return new Object[][]{{1}, {2}};
    }

    @SuppressWarnings({"Duplicates", "unchecked"})
    @Test(dataProvider = "seq_read_num")
    public void testSeqRead(int count) throws IOException, FileRemainSizeLessThanZeroException, CanNotWriteMoreObjectException {
        FileStorage<StorageData> fileStorage = new FileStorage(testFile, StorageData.class);

        for(int i=0; i<count; i++) {
            StorageData storageData = new StorageData();
            byte[] data = String.format("content_%d", i).getBytes();
            storageData.setData(data);

            fileStorage.append(storageData);
        }

        Iterator<StorageData> iterator = fileStorage.getIterator();
        int index = 0;
        while(iterator.hasNext()){
            StorageData storageData = iterator.next();
            byte[] expectData = String.format("content_%d", index).getBytes();
            index++;

            Assert.assertEquals(expectData, storageData.getData());
        }

        Assert.assertEquals(count, index);
    }

}