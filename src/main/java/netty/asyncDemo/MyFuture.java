package netty.asyncDemo;

import io.netty.util.concurrent.Future;

/**
 * @author hudi
 * @date 2021/02/23
 **/
public interface MyFuture<V> extends java.util.concurrent.Future<V>  {

    MyFuture<V> addListener(MyFutureListener<? extends MyFuture<? super V>> listener);

    MyFuture<V> removeListener(MyFutureListener<? extends MyFuture<? super V>> listener);



}
