package cn.zhanyongzhi.validationidea.netty.fileupload;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class FileUploadServer {
    private final int port;

    public FileUploadServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        ServerBootstrap server = new ServerBootstrap();

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            server.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class).localAddress(port)
                    .childHandler(new DispatcherServletChannelInitializer());

            server.bind().sync().channel().closeFuture().sync();
        }
        finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new FileUploadServer(port).run();
    }
}
