package cn.zhanyongzhi.pratice.studytestng;

import org.testng.annotations.Test;

public class ExceptionTest {
    @Test(expectedExceptions = {ArithmeticException.class})
    public void testArithmeticExcetpion(){
        int i = 1 / 0;
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testNullPointException(){
        Integer  foo = null;
        foo.toString();
    }
}
