package com.github.yongzhizhan.draftbox.springtest;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 被测类
 */
public class Foo {
    @Autowired
    private String m_String;

    @PostConstruct
    private void onStarted(){
        System.out.println("on started " + m_String);
    }

    @PreDestroy
    private void onStop(){
        System.out.println("on stop " + m_String);
    }
}
