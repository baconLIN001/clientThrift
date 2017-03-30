package com.bacon.client.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lee on 2017/3/14 0014.
 * 基于枚举的单例模式
 */
public enum  AsyncTaskUtils {
    INSTANCE;
    ExecutorService normalService=null;
    ScheduledExecutorService scheduledExecutorService=null;
    Logger log = LoggerFactory.getLogger(AsyncTaskUtils.class);

    private AsyncTaskUtils(){
        log.info("Threadpool Running!");
        normalService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);
        scheduledExecutorService = Executors.newScheduledThreadPool(10);

    }

    public synchronized boolean dispatchNormalTask(Runnable task) {
        if (!isNormalServiceAvailable()){
            log.warn("NormalThreadPoll is not Available!");
            return false;
        }else if (task==null){
            log.warn("Your task is null!");
            return false;
        }else {
            try {
                normalService.execute(task);
            } catch (Exception e) {
                log.error("" + e);
            }
        }
        return true;
    }

    public synchronized boolean dispatchScheduleTask(Runnable task,long delayTime,long frequency) {
        if (!isScheduleServiceAvailable()){
            log.warn("ScheduleService is not Available!");
            return false;
        }else if (task==null){
            log.warn("Your task is null!");
            return false;
        }else {
            try {
                delayTime = delayTime > 0L?delayTime:0L;
                frequency = frequency > 0L?frequency:0L;
                scheduledExecutorService.scheduleAtFixedRate(task,delayTime,frequency, TimeUnit.SECONDS);
            } catch (Exception e) {
                log.error("" + e);
            }
        }
        return true;
    }

    public synchronized boolean dispatchDelayTask(Runnable task,long delayTime) {
        if (!isScheduleServiceAvailable()){
            log.warn("ScheduleService is not Available!");
            return false;
        }else if (task==null){
            log.warn("Your task is null!");
            return false;
        }else {
            try {
                delayTime = delayTime > 0L?delayTime:0L;
                scheduledExecutorService.schedule(task,delayTime, TimeUnit.SECONDS);
            } catch (Exception e) {
                log.error("" + e);
            }
        }
        return true;
    }

    public synchronized void close() {
        if(this.scheduledExecutorService != null && !this.scheduledExecutorService.isShutdown()) {
            this.scheduledExecutorService.shutdownNow();
        }

        if(this.normalService != null && !this.normalService.isShutdown()) {
            this.normalService.shutdownNow();
        }

    }


    public synchronized boolean isNormalServiceAvailable() {
        return this.normalService != null && !this.normalService.isShutdown();
    }

    public synchronized boolean isScheduleServiceAvailable() {
        return this.scheduledExecutorService!=null&&!scheduledExecutorService.isShutdown();
    }
}
