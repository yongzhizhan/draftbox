package sdlx;

import junit.framework.Assert;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 获取翻新类型
 * author:zhanyongzhi
 */
public class GenericTypeTest {
    List<String> list;

    @Test
    public void testGenericType() throws NoSuchFieldException {
        java.lang.reflect.Field field = GenericTypeTest.class.getDeclaredField("list");
        java.lang.reflect.Type type = field.getGenericType();

        if(type instanceof ParameterizedType){
            java.lang.reflect.Type[] types = ((ParameterizedType) type).getActualTypeArguments();

            String genericTypeName = ((Class)types[0]).getName();
            Assert.assertEquals("java.lang.String", genericTypeName);

            return;
        }

        Assert.fail("can not run to here.");
    }
}
