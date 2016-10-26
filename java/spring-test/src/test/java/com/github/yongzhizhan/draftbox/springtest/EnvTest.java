package com.github.yongzhizhan.draftbox.springtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:**web-config.xml")
@ActiveProfiles("product")
public class EnvTest {
    @Autowired
    ITest test;

    @Autowired
    Environment env;

    @Test
    public void testDefault(){
        for(String profileName : env.getActiveProfiles())
            System.out.println(profileName);

        //perform current active bean
        test.perform();
    }
}