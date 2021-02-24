package netty.asyncDemo;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static io.netty.util.internal.ObjectUtil.checkNotNull;

/**
 * @author hudi
 * @date 2021/02/23
 **/
public class DefaultMyChannelPromise implements MyChannelPromise {
    private final MyChannel channel;
    private volatile Object result;
    private MyFutureListener listener;
    private static final Object SUCCESS = new Object();


    public DefaultMyChannelPromise(MyChannel channel) {
        this.channel = checkNotNull(channel, "channel");
    }


    @Override
    public MyChannel channel() {
        return channel;
    }

    @Override
    public MyChannelPromise addListener(MyFutureListener<? extends MyFuture<? super Void>> listener) {
        synchronized (this) {
            this.listener = listener;
        }

        if (isDone()) {
            notifyListener();
        }

        return this;
    }

    private void notifyListener() {
        try {
            listener.operationComplete(this);
        } catch (Throwable t) {
            System.out.println(("An exception was thrown by " + listener.getClass().getName() + ".operationComplete()" + t));
        }
    }


    @Override
    public MyChannelPromise removeListener(MyFutureListener<? extends MyFuture<? super Void>> listener) {
        synchronized (this) {
            this.listener = null;
        }

        return this;
    }

    @Override
    public MyChannelPromise setSuccess(Void result) {
        this.result = SUCCESS;
        notifyListener();
        return this;
    }

    @Override
    public MyChannelPromise setSuccess() {
        return setSuccess(null);
    }


    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return result != null && result == SUCCESS;
    }

    @Override
    public Void get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public Void get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
