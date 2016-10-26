package com.github.yongzhizhan.draftbox.java.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 *
 */
class ProductFuture<T> {
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private T content;
    private Object result;

    public ProductFuture(T content){
        setContent(content);
    }

    public void setDone(Object result){
        this.result = result;
        countDownLatch.countDown();
    }

    public void setContent(T content){
        this.content = content;
    }

    public T getContent(){
        return content;
    }

    public void setResult(Object result){
        this.result = result;
    }

    public Object getResult(){
        return result;
    }

    public boolean isDone(){
        return (countDownLatch.getCount() == 0);
    }

    public Object get(){
        return result;
    }

    public void await() throws InterruptedException {
        countDownLatch.await();
    }

    public void await(int timeout, TimeUnit unit) throws InterruptedException {
        countDownLatch.await(timeout, unit);
    }
}