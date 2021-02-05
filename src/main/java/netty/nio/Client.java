package netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Description
 * @Author hudi
 * @Date 2018/12/22 16:58
 * @Version 1.0
 **/
public class Client {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel=SocketChannel.open();
        socketChannel.configureBlocking(false);
        if (!socketChannel.connect(new InetSocketAddress("127.0.0.1",6666))) {
            while (!socketChannel.finishConnect()){
                System.out.println("连接中。。。");
            }
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap("hello,hudi".getBytes());
        socketChannel.write(byteBuffer);
        System.in.read();

    }
}
