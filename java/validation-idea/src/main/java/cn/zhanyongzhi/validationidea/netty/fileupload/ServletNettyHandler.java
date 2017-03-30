//package cn.zhanyongzhi.validationidea.netty.fileupload;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelFutureListener;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundMessageHandlerAdapter;
//import io.netty.handler.codec.http.DefaultHttpResponse;
//import io.netty.handler.codec.http.HttpRequest;
//import io.netty.handler.codec.http.HttpResponse;
//import io.netty.handler.codec.http.HttpResponseStatus;
//import io.netty.handler.stream.ChunkedStream;
//import io.netty.util.CharsetUtil;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.RandomAccessFile;
//import java.nio.channels.FileChannel;
//
//import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
//import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
//import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
//import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
//
//public class ServletNettyHandler extends ChannelInboundMessageHandlerAdapter<HttpRequest> {
//
//    @Override
//    public void messageReceived(ChannelHandlerContext ctx, HttpRequest request) throws Exception {
//        if (!request.getDecoderResult().isSuccess()) {
//            sendError(ctx, BAD_REQUEST);
//            return;
//        }
//
//        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, HttpResponseStatus.OK);
//
//        String uri = request.getUri();
//        if(uri.endsWith("/upload.html")){
//            String basePath = getClass().getClassLoader().getResource("").getPath().substring(1);
//            FileInputStream fileInputStream = new FileInputStream(new File(basePath + "/upload.html"));
//            ctx.write(response);
//            ChannelFuture future = ctx.write(new ChunkedStream(fileInputStream));
//            future.addListener(ChannelFutureListener.CLOSE);
//            return;
//        } else if(uri.endsWith("/upload.action")){
//            //buf = request.getContent();
//            RandomAccessFile raf = new RandomAccessFile("foobar.tmp","rw");
//            FileChannel channel = raf.getChannel();
//
//            String ret = "111111111111";
//            ByteBuf byteBuf = Unpooled.wrappedBuffer(ret.getBytes());
//
//            response.setContent(byteBuf);
//            ChannelFuture future = ctx.write(response);
//            future.addListener(ChannelFutureListener.CLOSE);
//            return;
//        }
//
//        ChannelFuture future = ctx.write(response);
//        future.addListener(ChannelFutureListener.CLOSE);
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        cause.printStackTrace();
//        if (ctx.channel().isActive()) {
//            sendError(ctx, INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
//        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, status);
//        response.setHeader(CONTENT_TYPE, "text/plain; charset=UTF-8");
//        response.setContent(Unpooled.copiedBuffer(
//                "Failure: " + status.toString() + "\r\n",
//                CharsetUtil.UTF_8));
//
//        // Close the connection as soon as the error message is sent.
//        ctx.write(response).addListener(ChannelFutureListener.CLOSE);
//    }
//
//}
