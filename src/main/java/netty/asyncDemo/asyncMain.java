package netty.asyncDemo;

import netty.asyncDemo.channel.MyChannel;
import netty.asyncDemo.channel.future.MyChannelFuture;

/**
 * @author hudi
 * @date 2021/02/23
 **/
public class asyncMain {

    public static void main(String[] args) {
        MyChannel channel = new MyChannel();
        MyChannelFuture f = channel.addTask();
        f.addListener(l -> {
            System.out.println("my 回调!!!");
        });
        MyChannelFuture cf = channel.addSleep1sTask();
        cf.addListener(l->{
            System.out.println("my 1s回调!!!");
        });
    }
}
