package com.github.yongzhizhan.draftbox.java.thread;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 测试
 */
public class DepotTest {
    @Test
    public void produce() throws Exception {
        final Depot<Integer> depot = new Depot<Integer>(100);
        ProductFuture<Integer> future = depot.produce(1);

        //start a thread
        ExecutorService consumerExecutor = Executors.newSingleThreadExecutor();
        consumerExecutor.submit(new Runnable() {
            public void run() {
                List<ProductFuture<Integer>> productFutures = depot.consume(10);
                for(ProductFuture<Integer> productFuture : productFutures){
                    Integer tVal = productFuture.getContent();

                    System.out.println(tVal);

                    try {
                        Thread.sleep(2 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    productFuture.setDone(123456);
                }
            }
        });

        //等待完成通知
        future.await();

        System.out.println(String.format("productFuture.await() complete result : %d", (Integer) future.getResult()));

        consumerExecutor.shutdown();
        consumerExecutor.awaitTermination(10, TimeUnit.SECONDS);
    }
}