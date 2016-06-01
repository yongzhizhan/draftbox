package com.github.yongzhizhan.draftbox.HttpNettyServer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wentian on 16/6/1.
 */
public class HttpChannelInitializer extends ChannelInitializer<SocketChannel> {
    protected List<HttpMessageHandler> httpMessageHandlers = new LinkedList<>();
    protected List<WebsocketMessageHandler> websocketMessageHandlers = new LinkedList<>();
    protected String wsPath;
    protected int maxContentSize;

    HttpChannelInitializer(List<HttpMessageHandler> httpMessageHandlers,
                           int maxContentSize,
                           List<WebsocketMessageHandler> websocketMessageHandlers,
                           String wsPath) {
        this.websocketMessageHandlers = websocketMessageHandlers;
        this.httpMessageHandlers = httpMessageHandlers;
        this.wsPath = wsPath;
        this.maxContentSize = maxContentSize;
    }

    HttpChannelInitializer(List<HttpMessageHandler> httpMessageHandlers,
                           List<WebsocketMessageHandler> websocketMessageHandlers,
                           String wsPath) {
        this(httpMessageHandlers, 64 * 1024, websocketMessageHandlers, wsPath);
    }

    HttpChannelInitializer(List<HttpMessageHandler> httpMessageHandlers) {
        this(httpMessageHandlers, 64 * 1024, null, null);
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        channelPipeline.addLast(new HttpServerCodec());
        channelPipeline.addLast(new HttpObjectAggregator(maxContentSize));
        channelPipeline.addLast(new ChunkedWriteHandler());

        if(null != httpMessageHandlers){
            for (HttpMessageHandler handler : httpMessageHandlers) {
                channelPipeline.addLast(new HttpHandler(handler, wsPath));
            }
        }

        if (null != wsPath && null != websocketMessageHandlers) {
            channelPipeline.addLast(new WebSocketServerProtocolHandler(wsPath));

            for (WebsocketMessageHandler handler : websocketMessageHandlers) {
                channelPipeline.addLast(new WebsocketHandler(handler));
            }
        }
    }
}
