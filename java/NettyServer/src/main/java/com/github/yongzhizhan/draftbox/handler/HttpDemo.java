package com.github.yongzhizhan.draftbox.handler;

import com.github.yongzhizhan.draftbox.HttpNettyServer.HttpMessageHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 * Created by wentian on 16/6/1.
 */
public class HttpDemo extends HttpMessageHandler {
    @Override
    public void handle(ChannelHandlerContext ctx, FullHttpRequest request, FullHttpResponse response) {
        response.content().writeBytes("hello".getBytes());
    }
}
