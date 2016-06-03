import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface IFoo{
    public void test();
}

class Foo implements IFoo{
    public void test(){
        System.out.println("test");
    }
}

class Handler implements InvocationHandler {
    private Foo foo;

    public Handler(Foo foo){
        this.foo = foo;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("handler begin");
        Object ret = method.invoke(foo, args);
        System.out.println("handler end");

        return ret;
    }
}

public class Main {
    public static void main(String[] args) {
        Foo foo = new Foo();
        Handler handler = new Handler(foo);

        try{
            IFoo fooProxy = (IFoo) Proxy.newProxyInstance(handler.getClass().getClassLoader(), foo.getClass().getInterfaces(), handler);
            fooProxy.test();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
