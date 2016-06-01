package com.github.yongzhizhan.draftbox.HttpNettyServer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

/**
 * Created by wentian on 16/6/1.
 */
public abstract class WebsocketMessageHandler {
    abstract public void handle(ChannelHandlerContext ctx, WebSocketFrame request, ChannelGroup channels);
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){}
}
