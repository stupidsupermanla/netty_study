package netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.simple.SHandler;
import netty.simple.Server;

/**
 * @Description
 * @Author hudi
 * @Date 2018/12/22 16:58
 * @Version 1.0
 **/
public class HttpServer {

    public static final int port = 8888;

    public void run() throws Exception {
        //bossGroup 用来接收进来的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //workerGroup 用来处理已经被接收的连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //启动 NIO 服务的辅助启动类
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HttpChannelInitializer());

            // 服务器绑定端口
            ChannelFuture f = b.bind(port).sync();

            f.addListener((ChannelFutureListener) future -> {
                System.out.println("启动成功！！！");
            });
            // 等待服务器 socket 关闭 。
            f.channel().closeFuture().sync();
        } finally {
            // 出现异常终止
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            System.out.println("连接关闭等异常");
        }
    }

    public static void main(String[] args) throws Exception {
        new HttpServer().run();
    }
}
