package netty.asyncDemo.channel.promise;


import netty.asyncDemo.channel.future.MyFutureListener;
import netty.asyncDemo.channel.MyChannel;
import netty.asyncDemo.channel.future.MyChannelFuture;
import netty.asyncDemo.channel.future.MyFuture;

/**
 * @author hudi
 * @date 2021/02/23
 **/
public interface MyChannelPromise extends MyChannelFuture, MyPromise<Void> {

    @Override
    MyChannel channel();

    @Override
    MyChannelPromise addListener(MyFutureListener<? extends MyFuture<? super Void>> listener);


    @Override
    MyChannelPromise removeListener(MyFutureListener<? extends MyFuture<? super Void>> listener);

    @Override
    MyChannelPromise setSuccess(Void result);

    MyChannelPromise setSuccess();

}
