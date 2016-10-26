package com.github.yongzhizhan.draftbox.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

@Component
public class TestBar {
    @Autowired
    Bar bar;

    public TestBar(){
        //
    }
}
