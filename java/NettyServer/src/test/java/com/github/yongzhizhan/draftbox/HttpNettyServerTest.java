package com.github.yongzhizhan.draftbox;

import com.github.yongzhizhan.draftbox.HttpNettyServer.HttpMessageHandler;
import com.github.yongzhizhan.draftbox.HttpNettyServer.HttpNettyServer;
import com.github.yongzhizhan.draftbox.HttpNettyServer.WebsocketMessageHandler;
import com.github.yongzhizhan.draftbox.handler.HttpDemo;
import com.github.yongzhizhan.draftbox.handler.WebsocketDemo;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.lf5.util.Resource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wentian on 16/5/31.
 */
public class HttpNettyServerTest {
    @Before
    public void setUp() throws Exception {
        PropertyConfigurator.configure(new Resource("log4j.config").getInputStream());
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDefault() {
        HttpNettyServer httpNettyServer = new HttpNettyServer(new InetSocketAddress("localhost", 9901));

        List<HttpMessageHandler> httpMessageHandlers = new LinkedList<>();
        httpMessageHandlers.add(new HttpDemo());

        List<WebsocketMessageHandler> websocketMessageHandlers = new LinkedList<>();
        websocketMessageHandlers.add(new WebsocketDemo());

        Assert.assertTrue(httpNettyServer.start(httpMessageHandlers, websocketMessageHandlers, "/ws"));
        Assert.assertTrue(httpNettyServer.stop());
    }

    @Test
    public void testWebsocket() {
        HttpNettyServer httpNettyServer = new HttpNettyServer(new InetSocketAddress("localhost", 9902));

        List<WebsocketMessageHandler> websocketMessageHandlers = new LinkedList<>();
        websocketMessageHandlers.add(new WebsocketDemo());

        Assert.assertTrue(httpNettyServer.start(websocketMessageHandlers, "/ws"));
        Assert.assertTrue(httpNettyServer.stop());
    }
}