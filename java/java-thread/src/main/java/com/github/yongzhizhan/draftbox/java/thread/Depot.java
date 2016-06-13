package com.github.yongzhizhan.draftbox.java.thread;

import java.util.LinkedList;
import java.util.List;

/**
 * 带通知的仓库
 */

public class Depot<T> {
    private List<ProductFuture<T>> productList = new LinkedList<ProductFuture<T>>();
    private int capacity;
    private boolean stop = false;

    public Depot(int capacity) {
        this.capacity = capacity;
    }

    public void shutdown(){
        stop = true;
        notifyAll();
    }

    public synchronized ProductFuture<T> produce(T product) {
        ProductFuture<T> productFuture = null;

        if(true == stop)
            return productFuture;

        try {
            if (productList.size() > capacity)
                wait();

            if(true == stop)
                return productFuture;

            //插入头
            productFuture = new ProductFuture<T>(product);
            productList.add(0, productFuture);

            //notify not empty
            notifyAll();
        }  catch (InterruptedException e) {
            e.printStackTrace();
        }

        return productFuture;
    }

    public synchronized List<ProductFuture<T>> consume(int maxCount) {
        List<ProductFuture<T>> consumeList = new LinkedList<ProductFuture<T>>();

        if(true == stop)
            return consumeList;

        try {
            if (productList.isEmpty())
                wait();

            if(true == stop)
                return consumeList;

            int count = Math.min(maxCount, productList.size());

            int index = productList.size() - count;
            consumeList.addAll(index, productList);
            productList.remove(index);

            //notify not full
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return consumeList;
    }
}
