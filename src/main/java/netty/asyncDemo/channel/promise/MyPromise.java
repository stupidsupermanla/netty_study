package netty.asyncDemo.channel.promise;


import netty.asyncDemo.channel.future.MyFuture;

/**
 * @author hudi
 * @date 2021/02/23
 **/
public interface MyPromise<V> extends MyFuture<V> {

    MyPromise<V> setSuccess(V result);


}
