package netty.asyncDemo;

import io.netty.util.concurrent.Future;

import java.util.EventListener;

/**
 * @author hudi
 * @date 2021/02/23
 **/
@FunctionalInterface
public interface MyFutureListener<F extends MyFuture<?>> extends EventListener {

    void operationComplete(F MyFuture) throws Exception;
}
