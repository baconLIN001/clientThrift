package com.bacon.client.others;

import com.bacon.client.utils.AsyncTaskUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by bacon on 2017/3/20.
 */
public class FutureTaskTest2 {
    public static void main(String[] args){
        FutureTaskTest2 inst = new FutureTaskTest2();
        //创建任务集合
        List<FutureTask<Integer>>taskList = new ArrayList<FutureTask<Integer>>();
        for (int i = 0 ; i < 10; i++){
            //传入Callable对象创建FutureTask对象
            FutureTask<Integer>futureTask = new FutureTask<Integer>(inst.new ComputeTask(i,""+i));
            taskList.add(futureTask);
            //线程池执行任务
            AsyncTaskUtils.INSTANCE.dispatchNormalTask(futureTask);
        }
        System.out.println("所有计算任务提交完毕, 主线程接着干其他事情！");

        //开始统计各计算线程计算结果
        Integer totalResult = 0;
        for (FutureTask<Integer> futureTask : taskList){
            try {
                //get()方法会自动阻塞，直到获取计算结果为止
                totalResult = totalResult + futureTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println("总结果是： " +totalResult);
    }

    private class ComputeTask implements Callable<Integer>{

        private Integer result = 0;
        private String taskName = "";

        public ComputeTask(Integer iniResult, String taskName){
            result = iniResult;
            this.taskName = taskName;
            System.out.println("生成子线程计算任务: " +taskName);
        }

        public String getTaskName(){
            return this.taskName;
        }

        @Override
        public Integer call() throws Exception {
            for (int i = 0; i<100;i++)
            {
                result += i;
            }
            // 休眠5秒钟，观察主线程行为，预期的结果是主线程会继续执行，到要取得FutureTask的结果是等待直至完成。
            Thread.sleep(5000);
            System.out.println("子线程计算任务: "+taskName+" 执行完成!");
            return result;
        }

    }
}
