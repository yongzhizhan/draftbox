package com.github.yongzhizhan.draftbox.springtest.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * 切面
 */
@Aspect
public class BarAspect {
    @Before(value = "execution(* com.github.yongzhizhan.draftbox.springtest.aop.Bar.perform(..))")
    public void beforeSayHello(JoinPoint vJoinPoint){
        System.out.println("aspect before "+vJoinPoint.getArgs()[0]);
    }
}
