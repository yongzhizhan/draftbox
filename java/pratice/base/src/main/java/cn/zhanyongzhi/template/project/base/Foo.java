package cn.zhanyongzhi.template.project.base;

import cn.zhanyongzhi.template.project.dubbointerface.IFoo;
import org.springframework.stereotype.Component;

@Component
public class Foo implements IFoo {
    @Override
    public String output(String val){
        return "Foo " + val;
    }
    public void run(){
        System.out.println("Hello Foo");
    }
}
