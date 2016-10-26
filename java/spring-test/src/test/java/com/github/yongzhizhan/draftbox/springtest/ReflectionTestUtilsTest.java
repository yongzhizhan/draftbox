package com.github.yongzhizhan.draftbox.springtest;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * 使用ReflectionTestUtils解决依赖注入
 * @author zhanyongzhi
 */
public class ReflectionTestUtilsTest {
    @Test
    public void testDefault(){
        Foo tFoo = new Foo();

        //set private property
        ReflectionTestUtils.setField(tFoo, "m_String", "Hello");

        //invoke construct and destroy method
        ReflectionTestUtils.invokeMethod(tFoo, "onStarted");
        ReflectionTestUtils.invokeMethod(tFoo, "onStop");
    }
}
