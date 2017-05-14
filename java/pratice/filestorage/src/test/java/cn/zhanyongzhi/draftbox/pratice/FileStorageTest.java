package cn.zhanyongzhi.draftbox.pratice;

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
    public void testDefault() throws IllegalAccessException, InstantiationException, IOException {
        FileStorage<Foo> fileStorage = new FileStorage(testFile, Foo.class);

        Foo foo = new Foo();
        foo.setData("123456".getBytes());

        fileStorage.append(foo);

        Foo readFoo = fileStorage.getObject(0);

        Assert.assertEquals(true, Arrays.equals(readFoo.getData(), foo.getData()));
    }

    @SuppressWarnings({"Duplicates", "unchecked"})
    @Test
    public void testRemoveBlock() throws IOException {
        FileStorage<Foo> fileStorage = new FileStorage(testFile, Foo.class);

        int count = 10;

        for(int i=0; i<count; i++) {
            Foo foo = new Foo();
            byte[] data = String.format("content_%d", i).getBytes();
            foo.setData(data);

            fileStorage.append(foo);
        }

        for(int i=0; i<count; i++){
            fileStorage.delete(i);
        }

        Iterator<Foo> iterator = fileStorage.getIterator();
        while(iterator.hasNext()){
            Foo foo = iterator.next();
            Assert.assertTrue(foo.isDelete());
        }
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void testCompact() throws IOException, InterruptedException {
        FileStorage<Foo> fileStorage = new FileStorage(testFile, Foo.class);

        int count = 10;
        int curIndex = 0;

        List<Integer> indexList = new ArrayList(count);

        for(int i=0; i<count; i++) {
            Foo foo = new Foo();
            byte[] data = String.format("content_%d", i).getBytes();
            foo.setData(data);

            curIndex = fileStorage.append(foo);
            indexList.add(curIndex);
        }

        for(int i=0; i<count - 1; i++){
            fileStorage.delete(indexList.get(i));
        }

        fileStorage.compact();

        Thread.sleep(10 * 1000);

        int index = 0;
        Iterator<Foo> iterator = fileStorage.getIterator();
        while(iterator.hasNext()){
            index++;

            Foo foo = iterator.next();
            Assert.assertFalse(foo.isDelete());
        }

        Assert.assertTrue(1 == index);
    }

    @Test
    public void testCheckSum() throws IOException, IllegalAccessException, InstantiationException {
        FileStorage<Foo> fileStorage = new FileStorage(testFile, Foo.class);
        int lastIndex = 0;

        for(int i=0; i<10; i++) {
            Foo foo = new Foo();
            byte[] data = String.format("content_%d", i).getBytes();
            foo.setData(data);

            lastIndex = fileStorage.append(foo);
        }

        Foo foo = fileStorage.getObject(lastIndex);

        Assert.assertFalse(foo.isDelete());
    }
}
