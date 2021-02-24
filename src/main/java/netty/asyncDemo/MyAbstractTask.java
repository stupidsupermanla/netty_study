package netty.asyncDemo;


/**
 * @author hudi
 * @date 2021/02/23
 **/
public abstract class MyAbstractTask implements Runnable{

    private MyChannelPromise promise;

    public MyAbstractTask(MyChannelPromise promise) {
        this.promise = promise;
    }


    public MyChannelPromise getPromise() {
        return promise;
    }


    protected void running(){
        System.out.println("sleeping 5 秒！！！");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void runStart(){
        System.out.println("task start！！！");
    }
    protected void runEnd(){
        System.out.println("task end！！！");
    }

    public void success(){
        promise.setSuccess();
    }

    @Override
    public void run() {
        runStart();
        running();
        runEnd();
        success();
    }
}
