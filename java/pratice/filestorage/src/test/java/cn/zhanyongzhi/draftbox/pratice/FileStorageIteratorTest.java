package cn.zhanyongzhi.draftbox.pratice;

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
    public void testSeqRead(int count) throws IOException {
        FileStorage<Foo> fileStorage = new FileStorage(testFile, Foo.class);

        for(int i=0; i<count; i++) {
            Foo foo = new Foo();
            byte[] data = String.format("content_%d", i).getBytes();
            foo.setData(data);

            fileStorage.append(foo);
        }

        Iterator<Foo> iterator = fileStorage.getIterator();
        int index = 0;
        while(iterator.hasNext()){
            Foo foo = iterator.next();
            byte[] expectData = String.format("content_%d", index).getBytes();
            index++;

            Assert.assertEquals(expectData, foo.getData());
        }

        Assert.assertEquals(count, index);
    }

}