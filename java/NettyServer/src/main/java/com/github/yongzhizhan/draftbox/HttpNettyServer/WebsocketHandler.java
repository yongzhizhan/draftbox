package com.github.yongzhizhan.draftbox.HttpNettyServer;

import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.log4j.Logger;

/**
 * Created by wentian on 16/5/31.
 */
public class WebsocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    private static Logger log = Logger.getLogger(WebsocketHandler.class.getName());
    private WebsocketMessageHandler websocketMessageHandler = null;
    private String ignorePaths;

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    WebsocketHandler(WebsocketMessageHandler handler) {
        this.websocketMessageHandler = handler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame request) throws Exception {
        log.trace(request.toString());
        websocketMessageHandler.handle(ctx, request, channels);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
        Channel incoming = ctx.channel();
        channels.add(incoming);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        Channel incoming = ctx.channel();

        log.info("Client:" + incoming.remoteAddress() + "异常");
        websocketMessageHandler.exceptionCaught(ctx, cause);

        ctx.close();
    }
}
