package com.github.yongzhizhan.draftbox.handler;

import com.github.yongzhizhan.draftbox.HttpNettyServer.WebsocketMessageHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

/**
 * Created by wentian on 16/6/1.
 */
public class WebsocketDemo extends WebsocketMessageHandler {
    @Override
    public void handle(ChannelHandlerContext ctx, WebSocketFrame request, ChannelGroup channels) {
        ctx.writeAndFlush(new TextWebSocketFrame("hello, count:" + channels.size()));
    }
}
