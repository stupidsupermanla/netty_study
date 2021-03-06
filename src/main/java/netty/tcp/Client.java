package netty.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import netty.nettyGroupChat.ClientHandler;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @Description
 * @Author hudi
 * @Date 2018/12/22 16:58
 * @Version 1.0
 **/
public class Client {
    private final String host;
    private final int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap().group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
//                        pipeline.addLast("d", new StringDecoder());
                        pipeline.addLast(new TcpClientHandler());
//                        pipeline.addLast("e", new StringEncoder());
                    }
                });
        ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
        Channel channel = channelFuture.channel();
        System.out.println("客户端:" + channel.localAddress() + "启动！！");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            ByteBuf byteBuf = Unpooled.copiedBuffer(s, StandardCharsets.UTF_8);
            channel.writeAndFlush(byteBuf);
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Client client = new Client("127.0.0.1", 8888);
        client.run();
    }

}
