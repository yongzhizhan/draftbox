package com.github.yongzhizhan.draftbox.HttpNettyServer;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import org.apache.log4j.Logger;

/**
 * Created by wentian on 16/5/31.
 */
public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static Logger log = Logger.getLogger(HttpHandler.class.getName());
    private HttpMessageHandler httpMessageHandler = null;
    private String wspPath;

    HttpHandler(HttpMessageHandler handler, String ignorePaths) {
        this.httpMessageHandler = handler;
        this.wspPath = ignorePaths;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        log.trace(request.toString());

        //判断是否为websocket消息
        if(null != wspPath) {
            if (request.getUri().equalsIgnoreCase(wspPath)){
                ctx.fireChannelRead(request.retain());
                return;
            }
        }

        if (HttpHeaders.is100ContinueExpected(request)) {
            send100Continue(ctx);
        }

        DefaultFullHttpResponse response = new DefaultFullHttpResponse(request.getProtocolVersion(), HttpResponseStatus.OK);
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");

        boolean keepAlive = HttpHeaders.isKeepAlive(request);

        httpMessageHandler.handle(ctx, request, response);

        if (keepAlive) {
            response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, response.content().writerIndex());
            response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }

        Channel incoming = ctx.channel();

        incoming.write(response);

        ChannelFuture future = incoming.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        if (!keepAlive) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private static void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        Channel incoming = ctx.channel();

        log.info("Client:" + incoming.remoteAddress() + "异常");
        httpMessageHandler.exceptionCaught(ctx, cause);

        ctx.close();
    }
}
