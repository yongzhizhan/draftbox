package com.github.yongzhizhan.draftbox.HttpNettyServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Created by wentian on 16/5/31.
 */
public class HttpNettyServer {
    private static Logger log = Logger.getLogger(HttpNettyServer.class.getName());

    protected int numWorkThread = Runtime.getRuntime().availableProcessors() * 2;
    protected InetSocketAddress socketAddress = null;
    protected ChannelFuture bindChannelFuture = null;
    protected EventLoopGroup workEventLoopGroup = null;

    public HttpNettyServer(InetSocketAddress socketAddress) {
        this(socketAddress, null);
    }

    public HttpNettyServer(InetSocketAddress socketAddress, Integer numWorkThread) {
        this.socketAddress = socketAddress;

        if (null != numWorkThread)
            this.numWorkThread = numWorkThread;
    }

    public boolean start(List<HttpMessageHandler> httpMessageHandlers,
                         int maxContentSize,
                         List<WebsocketMessageHandler> websocketMessageHandlers,
                         String wsPath) {
        ServerBootstrap bootstrap = new ServerBootstrap();

        workEventLoopGroup = new NioEventLoopGroup(numWorkThread);
        bootstrap.group(workEventLoopGroup);

        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        bootstrap.option(ChannelOption.SO_SNDBUF, 256 * 1024);
        bootstrap.option(ChannelOption.SO_RCVBUF, 64 * 1024);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);

        bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        bootstrap.option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT);

        ChannelInitializer channelInitializer = new HttpChannelInitializer(httpMessageHandlers,
                maxContentSize, websocketMessageHandlers, wsPath);

        bootstrap.childHandler(channelInitializer);

        bindChannelFuture = bootstrap.bind(socketAddress.getHostString(), socketAddress.getPort());

        try {
            if (true == bindChannelFuture.await().isSuccess()){
                log.info("server started bind: " + socketAddress.getPort());
                return true;
            }

            workEventLoopGroup.shutdownGracefully().await();
        } catch (InterruptedException e) {
            log.error(e);
        }

        return false;
    }

    public boolean start(List<HttpMessageHandler> httpMessageHandlers,
                         List<WebsocketMessageHandler> websocketMessageHandlers,
                         String wsPath) {
        return start(httpMessageHandlers, 64 * 1024, websocketMessageHandlers, wsPath);
    }

    public boolean start(List<WebsocketMessageHandler> websocketMessageHandlers, String wsPath) {
        return start(null, 64 * 1024, websocketMessageHandlers, wsPath);
    }

    public boolean start(List<HttpMessageHandler> httpMessageHandlers) {
        return start(httpMessageHandlers, 64 * 1024, null, null);
    }

    public boolean stop() {
        try {
            return workEventLoopGroup.shutdownGracefully().await().isSuccess();
        } catch (InterruptedException e) {
            log.error(e);
        }

        return false;
    }

    public boolean join() {
        final HttpNettyServer that = this;

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                that.stop();
            }
        });

        try {
            bindChannelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error(e);
            return false;
        }

        return true;
    }
}
