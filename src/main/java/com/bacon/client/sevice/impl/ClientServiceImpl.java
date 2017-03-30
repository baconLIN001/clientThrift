package com.bacon.client.sevice.impl;

import com.bacon.client.entity.RequestException;
import com.bacon.client.entity.RequestType;
import com.bacon.client.entity.WebRequest;
import com.bacon.client.pojo.JsonRootBean;
import com.bacon.client.pojo.Parameter;
import com.bacon.client.pojo.ReturnBack;
import com.bacon.client.sevice.ClientService;
import com.bacon.client.task.*;
import com.bacon.client.task.callable.FileUploadCallableTask;
import com.bacon.client.utils.AsyncTaskUtils;
import com.bacon.client.utils.JsonHandleUtils;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by bacon on 2017/3/17.
 */
public class ClientServiceImpl implements ClientService.Iface{

    Logger logger = Logger.getLogger(ClientService.class);


    @Override
    public String receive(WebRequest webRequest) throws RequestException, TException {
        RequestType type = webRequest.getRequestType();

        Parameter parameterBean = JsonHandleUtils.jsonToParameterbean(webRequest.parameter);

        if (type==RequestType.OFFLINE_DATA_UPLOAD)
        {
//            FileUploadTask fileUploadTask = new FileUploadTask(2,parameterBean);
            FileUploadCallableTask fileUploadCallableTask = new FileUploadCallableTask(2,parameterBean);
            logger.info("\nTurn to File Upload task executor");
            FutureTask<ReturnBack> futureTask = new FutureTask<ReturnBack>(fileUploadCallableTask){
                //异步任务执行完成，进行回调
                @Override
                protected void done(){
                    //获取future task返回值
                    ReturnBack returnBack = null;
                    try {
                        //阻塞，等待异步任务执行完毕-获取异步任务的返回值
//                        System.out.println("future.get(): " + get());
                        returnBack = get();
                        logger.info("TaskId: " + returnBack.getTaskId() + "    Info: " + returnBack.getReturnInfo());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            };
            AsyncTaskUtils.INSTANCE.dispatchNormalTask(futureTask);
            logger.info("\nExecuting future task...");

        }else if (type==RequestType.STREAM_DATA_UPLOAD)
        {
            AsyncTaskUtils.INSTANCE.dispatchNormalTask(new StreamdataUploadTask());
            logger.info("\nTurn to StreamData Upload task executor");
        }else if (type==RequestType.DATABASE_DATA_UPLOAD)
        {
            AsyncTaskUtils.INSTANCE.dispatchNormalTask(new DatabaseUploadTask());
            logger.info("\nTurn to Database Upload task executor");
        }else if (type==RequestType.FILELIST_VIEW)
        {
            AsyncTaskUtils.INSTANCE.dispatchNormalTask(new FileListViewTask());
            logger.info("\nTurn to FileList View task executor");
        }else if (type==RequestType.OFFline_File_PREIVEW)
        {
            AsyncTaskUtils.INSTANCE.dispatchNormalTask(new FilePreviewTask());
            logger.info("\nTurn to File Preview task executor");
        }else if (type==RequestType.GET_LIVE_CLIENTS)
        {
            AsyncTaskUtils.INSTANCE.dispatchNormalTask(new GetLiveClients());
            logger.info("\nTurn to Get Live Clients task executor");
        }
        else {
            logger.error("Unrecognized Request type");
            return "Unrecognized Request type";
        }

        return "Task Running";
    }
}
