package com.bacon.client.others;

import java.util.concurrent.Callable;

/**
 * Created by bacon on 2017/3/20.
 */
public class Task implements Callable<Integer> {
    //返回异步任务执行的结果
    @Override
    public Integer call() throws Exception {
        int i = 0;
        for (; i < 10; i++)
        {
            try {
                System.out.println(Thread.currentThread().getName()+"_"+i);
                Thread.sleep(1000);
            }catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        return i;
    }
}
