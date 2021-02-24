package netty.asyncDemo;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author hudi
 * @date 2021/02/23
 **/
public interface MyChannelFuture extends MyFuture<Void> {
    /**
     * Returns a channel where the I/O operation associated with this
     * future takes place.
     */
    MyChannel channel();

    @Override
    MyChannelFuture addListener(MyFutureListener<? extends MyFuture<? super Void>> listener);

    @Override
    MyChannelFuture removeListener(MyFutureListener<? extends MyFuture<? super Void>> listener);


}
