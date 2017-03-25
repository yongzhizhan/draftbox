package sdlx;

import junit.framework.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * 动态代理学习
 * author:zhanyongzhi
 */

public class DynamicProxyTest {
    @Test
    public void testProxyList() {
        final List list = new ArrayList();

        List list_proxy = (List) Proxy.newProxyInstance(DynamicProxyTest.class.getClassLoader(), new Class[] {List.class}, new InvocationHandler() {
            public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
                return method.invoke(list, args);
            }
        });

        list_proxy.add(0, "test");

        Assert.assertEquals(1, list_proxy.size());
        Assert.assertEquals(list.size(), list_proxy.size());

        Assert.assertFalse(list == list_proxy);
    }
}
