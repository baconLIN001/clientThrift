package com.bacon.client.others;

import com.bacon.client.utils.AsyncTaskUtils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by bacon on 2017/3/20.
 */
public class FutureTaskTest {
    public static void main(String[] args)
    {
        //新建异步任务
        Task task = new Task();
        FutureTask<Integer> futureTask = new FutureTask<Integer>(task){
            //异步任务执行完成，进行回调
            @Override
            protected void done(){
                try {
                    System.out.println("future.done(): " +get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        //调用线程池执行futuretask任务
        AsyncTaskUtils.INSTANCE.dispatchNormalTask(futureTask);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //取消异步任务,进行中断
//        futureTask.cancel(true);

        try {
            //阻塞，等待异步任务执行完毕-获取异步任务的返回值
            System.out.println("future.get(): " + futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
