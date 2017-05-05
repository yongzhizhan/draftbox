package cn.zhanyongzhi.template.project.base;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Main {
    private static Logger logger = Logger.getLogger(Main.class);
    private static ClassPathXmlApplicationContext applicationContext;

    @Autowired
    Foo foo;

    public static void main(String[] args){
        PropertyConfigurator.configure(ClassLoader.getSystemResource("log4j.properties"));

        try {
            applicationContext = new ClassPathXmlApplicationContext("classpath:META-INF/spring/*.xml");
            applicationContext.start();
        }catch(Exception e){
            logger.error("init application content failed", e);
            return;
        }

        Main main = (Main) applicationContext.getBean("main");
        main.foo.run();
    }
}
