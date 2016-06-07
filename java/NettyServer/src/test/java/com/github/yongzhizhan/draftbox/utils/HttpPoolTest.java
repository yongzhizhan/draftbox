package com.github.yongzhizhan.draftbox.utils;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class HttpPoolTest {
    @Test
    public void get() throws Exception {
        HttpPool httpPool = new HttpPool(100, 2000, 2000);

        StringBuilder response = new StringBuilder();
        int statusCode = httpPool.get("http://www.126.com", response);

        if(200 != statusCode)
            Assert.assertTrue(String.format("status code(%d) not equal 200", statusCode), false);

        System.out.println(response.toString());
    }

    @Test
    public void post() throws Exception {
        HttpPool httpPool = new HttpPool(100, 2000, 2000);

        StringBuilder response = new StringBuilder();
        int statusCode = httpPool.post("http://stackoverflow.com/", null, response);

        if(200 != statusCode)
            Assert.assertTrue(String.format("status code(%d) not equal 200", statusCode), false);

        System.out.println(response.toString());
    }
}