package netty.asyncDemo;


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
