package cn.zhanyongzhi.draftbox.pratice;

import cn.zhanyongzhi.draftbox.pratice.exception.CanNotWriteMoreObjectException;
import cn.zhanyongzhi.draftbox.pratice.exception.FileRemainSizeLessThanZeroException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FileStorageTest {
    private File testFile;

    @SuppressWarnings({"ResultOfMethodCallIgnored", "ConstantConditions"})
    @BeforeMethod
    public void setup() throws IOException {
        String pathName = getClass().getClassLoader().getResource("").getFile() + "/foo.data";
        testFile = new File(pathName);

        if(testFile.exists())
            testFile.delete();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDefault() throws IllegalAccessException, InstantiationException, IOException, FileRemainSizeLessThanZeroException, CanNotWriteMoreObjectException {
        FileStorage<StorageData> fileStorage = new FileStorage(testFile, StorageData.class);

        StorageData storageData = new StorageData();
        storageData.setData("123456".getBytes());

        Long key = fileStorage.append(storageData);

        StorageData readStorageData = fileStorage.getObject(key);

        Assert.assertEquals(true, Arrays.equals(readStorageData.getData(), storageData.getData()));
    }

    @SuppressWarnings({"Duplicates", "unchecked"})
    @Test
    public void testRemoveBlock() throws IOException, FileRemainSizeLessThanZeroException, CanNotWriteMoreObjectException {
        FileStorage<StorageData> fileStorage = new FileStorage(testFile, StorageData.class);

        int count = 10;
        List<Long> keyList = new ArrayList(count);

        for(int i=0; i<count; i++) {
            StorageData storageData = new StorageData();
            byte[] data = String.format("content_%d", i).getBytes();
            storageData.setData(data);

            Long key = fileStorage.append(storageData);
            keyList.add(key);
        }

        for(int i=0; i<count; i++){
            Long key = keyList.get(i);
            fileStorage.delete(key);
        }

        Iterator<StorageData> iterator = fileStorage.getIterator();
        while(iterator.hasNext()){
            StorageData storageData = iterator.next();
            Assert.assertTrue(storageData.isDelete());
        }
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void testCompact() throws IOException, InterruptedException, FileRemainSizeLessThanZeroException, CanNotWriteMoreObjectException {
        FileStorage<StorageData> fileStorage = new FileStorage(testFile, StorageData.class);

        int count = 10;
        long curKey = 0;

        List<Long> keyList = new ArrayList(count);

        for(int i=0; i<count; i++) {
            StorageData storageData = new StorageData();
            byte[] data = String.format("content_%d", i).getBytes();
            storageData.setData(data);

            curKey = fileStorage.append(storageData);
            keyList.add(curKey);
        }

        for(int i=0; i<count - 1; i++){
            fileStorage.delete(keyList.get(i));
        }

        fileStorage.compact();

        Thread.sleep(10 * 1000);

        int index = 0;
        Iterator<StorageData> iterator = fileStorage.getIterator();
        while(iterator.hasNext()){
            index++;

            StorageData storageData = iterator.next();
            Assert.assertFalse(storageData.isDelete());
        }

        Assert.assertTrue(1 == index);
    }

    @Test
    public void testCheckSum() throws IOException, IllegalAccessException, InstantiationException, FileRemainSizeLessThanZeroException, CanNotWriteMoreObjectException {
        FileStorage<StorageData> fileStorage = new FileStorage(testFile, StorageData.class);
        long lastKey = 0;

        for(int i=0; i<10; i++) {
            StorageData storageData = new StorageData();
            byte[] data = String.format("content_%d", i).getBytes();
            storageData.setData(data);

            lastKey = fileStorage.append(storageData);
        }

        StorageData storageData = fileStorage.getObject(lastKey);

        Assert.assertFalse(storageData.isDelete());
    }
}
