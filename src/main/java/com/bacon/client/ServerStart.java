package com.bacon.client;

import com.bacon.client.task.httpTasks.HttpGetAliveTask;
import com.bacon.client.thrift.RequestReceiver;
import com.bacon.client.utils.AsyncTaskUtils;
import org.apache.log4j.Logger;

/**
 * Created by bacon on 2017/3/17.
 */
public class ServerStart {

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(ServerStart.class);
//        new Thread(RequestReceiver.getInstance()).start();
        RequestReceiver requestReceiver = RequestReceiver.getInstance();
        requestReceiver.initReceiver();
        logger.info("Start Server!");

        HttpGetAliveTask httpGetAliveTask = new HttpGetAliveTask();
        AsyncTaskUtils.INSTANCE.dispatchNormalTask(httpGetAliveTask);
    }
}