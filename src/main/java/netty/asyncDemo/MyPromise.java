package netty.asyncDemo;


/**
 * @author hudi
 * @date 2021/02/23
 **/
public interface MyPromise<V> extends MyFuture<V> {

    MyPromise<V> setSuccess(V result);


}
