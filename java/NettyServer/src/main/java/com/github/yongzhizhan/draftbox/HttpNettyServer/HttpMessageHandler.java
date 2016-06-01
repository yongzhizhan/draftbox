package com.github.yongzhizhan.draftbox.HttpNettyServer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 * Created by wentian on 16/6/1.
 */
public abstract class HttpMessageHandler {
    abstract public void handle(ChannelHandlerContext ctx, FullHttpRequest request, FullHttpResponse response);
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){}
}
