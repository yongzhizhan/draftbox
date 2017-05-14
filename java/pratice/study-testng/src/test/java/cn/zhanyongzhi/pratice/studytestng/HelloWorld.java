package cn.zhanyongzhi.pratice.studytestng;


import junit.framework.Assert;
import org.testng.annotations.*;

public class HelloWorld {
    @BeforeClass
    public void setup(){
        System.out.println("before class");
    }

    @AfterClass
    public void teraDown(){
        System.out.println("after class");
    }

    @BeforeTest
    public void beforeTest(){
        System.out.println("Before test");
    }

    @BeforeMethod
    public void beforeMethod(){
        System.out.println("before method");
    }

    @Test
    public void testDefault(){
        System.out.println("hello world");
        Assert.assertTrue(true);
    }

    @Test
    public void testFoo(){
        System.out.println("test foo");
        Assert.assertTrue(true);
    }
}
