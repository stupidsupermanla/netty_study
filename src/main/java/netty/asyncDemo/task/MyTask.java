package netty.asyncDemo.task;

import netty.asyncDemo.channel.promise.MyChannelPromise;

/**
 * @author hudi
 * @date 2021/02/24
 **/
public class MyTask extends MyAbstractTask {

    public MyTask(MyChannelPromise promise) {
        super(promise);
    }

}
