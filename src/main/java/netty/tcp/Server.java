package netty.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import netty.nettyGroupChat.ServerHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author hudi
 * @Date 2018/12/22 16:58
 * @Version 1.0
 **/
public class Server {

    int port;

    public Server(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {
        //bossGroup 用来接收进来的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //workerGroup 用来处理已经被接收的连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 两个reactor 线程组
            bootstrap.group(bossGroup, workerGroup)
                    //反射创建channel
                    .channel(NioServerSocketChannel.class)
                    // option作用于main reactor
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // childOption作用于sub reactor
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
//                            pipeline.addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS));
//                            pipeline.addLast("d",new StringDecoder());
                            pipeline.addLast(new TcpServerHandlerA());
                            pipeline.addLast(new TcpServerHandlerB());
                            pipeline.addLast(new TcpServerHandlerC());
                            pipeline.addLast(new TcpServerHandlerD());
                            pipeline.addLast(new TcpServerHandlerE());
                            pipeline.addLast(new TcpServerHandlerF());
                            pipeline.addLast(new TcpServerHandler());
                            pipeline.addLast("e",new StringEncoder());
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
        new Server(8888).run();
    }

}
