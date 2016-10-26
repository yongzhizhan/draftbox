    package com.github.yongzhizhan.draftbox.springtest;

    import com.github.yongzhizhan.draftbox.springtest.aop.Bar;
    import com.github.yongzhizhan.draftbox.springtest.aop.BarAspect;
    import com.github.yongzhizhan.draftbox.springtest.aop.Dep;
    import com.github.yongzhizhan.draftbox.springtest.aop.IBar;
    import org.junit.Assert;
    import org.junit.Before;
    import org.junit.Test;
    import org.junit.runner.RunWith;
    import org.mockito.InjectMocks;
    import org.mockito.Mock;
    import org.mockito.MockitoAnnotations;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.ApplicationContext;
    import org.springframework.test.context.ContextConfiguration;
    import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
    import org.springframework.test.util.AopTestUtils;
    import org.springframework.test.util.ReflectionTestUtils;

    import static org.mockito.Mockito.when;

    /**
     * 通过AopTestUtils解决ReflectionTestUtils赋值切面对象的问题
     * @author zhanyongzhi
     */
    @RunWith(SpringJUnit4ClassRunner.class)
    @ContextConfiguration("classpath*:**web-config.xml")
    public class AopTestUtilsTest {
        @Mock
        Dep dep;

        @Autowired
        private BarAspect barAspect;

        @Autowired
        ApplicationContext applicationContext;

        @Autowired
        @InjectMocks
        IBar bar;

        @Before
        public void setUp(){
            MockitoAnnotations.initMocks(this);

            //对象默认返回aspect,修改为返回mock
            when(dep.perform("aspect")).thenReturn("mock");
        }

        @Test
        public void testDefault(){
            String tRet = bar.perform("hello");

            //mock注入无效,仍然返回aspect
            if(!"aspect".equals(tRet))
                Assert.fail("perform return not equeal aspect");
        }

        @Test
        public void testAopUtils(){

            //获取真实的代理对象
            Bar tBar = AopTestUtils.getTargetObject(bar);
            ReflectionTestUtils.setField(tBar, "dep", dep);

            String tRet = bar.perform("hello");

            //此时才真正mock到
            if(!"mock".equals(tRet))
                Assert.fail("perform return not equeal mock");
        }
    }
