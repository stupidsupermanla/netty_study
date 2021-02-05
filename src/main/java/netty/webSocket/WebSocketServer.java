package netty.webSocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import netty.nettyGroupChat.Server;
import netty.nettyGroupChat.ServerHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author hudi
 * @Date 2018/12/22 16:58
 * @Version 1.0
 **/
public class WebSocketServer {

    int port;

    public WebSocketServer(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {
        //bossGroup 用来接收进来的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //workerGroup 用来处理已经被接收的连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                           pipeline.addLast(new HttpServerCodec());
                           pipeline.addLast(new ChunkedWriteHandler());
                           pipeline.addLast(new HttpObjectAggregator(8194));
                           pipeline.addLast(new WebSocketServerProtocolHandler("/hudi"));
                           pipeline.addLast(new MyWebSocketHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind(port).sync();
            future.addListener((ChannelFutureListener) f -> {
                System.out.println("启动成功！！！");
            });
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            System.out.println("关闭成功！！！");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new WebSocketServer(8888).run();
    }
}
