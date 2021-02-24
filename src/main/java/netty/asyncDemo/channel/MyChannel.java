package netty.asyncDemo.channel;

import lombok.SneakyThrows;
import netty.asyncDemo.channel.promise.DefaultMyChannelPromise;
import netty.asyncDemo.channel.future.MyChannelFuture;
import netty.asyncDemo.channel.promise.MyChannelPromise;
import netty.asyncDemo.task.MyAbstractTask;
import netty.asyncDemo.task.MyTask;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hudi
 * @date 2021/02/23
 **/
public class MyChannel {

    private BlockingQueue<Runnable> queue;
    private Thread thread;
    private ExecutorService taskThread;

    class MyThread extends Thread {
        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                MyAbstractTask task = (MyAbstractTask) queue.take();
                taskThread.submit(task);
            }
        }
    }

    public MyChannel() {
        queue = new ArrayBlockingQueue<>(1024);
        taskThread = Executors.newSingleThreadExecutor();
        thread = new MyThread();
        thread.start();
    }

    public MyChannelFuture addTask() {
        MyTask task = new MyTask(new DefaultMyChannelPromise(this));
        queue.add(task);
        return task.getPromise();
    }

    public MyChannelFuture addSleep1sTask() {
        Sleep1sTask task = new Sleep1sTask(new DefaultMyChannelPromise(this));
        queue.add(task);
        return task.getPromise();
    }

    class Sleep1sTask extends MyAbstractTask {

        public Sleep1sTask(MyChannelPromise promise) {
            super(promise);
        }

        @Override
        protected void running() {
            System.out.println("running 1 秒！！！");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // public MyChannelFuture getMyChannelFuture() {
    //     return new DefaultMyChannelPromise(this);
    // }

}
