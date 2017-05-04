package com.bacon.client.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.b3434.Factory.ProcessorFactory;
import com.bacon.client.api.http.HttpReturnBackTask;
import com.bacon.client.api.pojo.RequestReturnBack;
import com.bacon.client.common.entity.*;
import com.bacon.client.common.service.ClientService;
import com.bacon.client.common.util.AsyncTaskUtils;
import com.bacon.client.common.util.FileUtils;
import com.bacon.client.core.avro.DbHandler;
import com.bacon.client.core.avro.FileHandle;
import com.bacon.client.core.base.FileUploadCode;
import com.bacon.client.core.callable.FileListUploadCallableTask;
import com.bacon.client.core.callable.FileUploadCallableTask;
import com.bacon.client.core.element.CompleteReturnBack;
import com.bacon.client.core.element.DbConstant;
import com.bacon.client.core.runnable.DatabaseUploadTask;
import com.bacon.client.core.runnable.FileListViewTask;
import com.bacon.client.core.runnable.FilePreviewTask;
import com.bacon.client.core.runnable.GetLiveClients;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by bacon on 2017/4/24.
 */
public class ClientAsyncServiceImpl implements ClientService.Iface {
    Logger logger = LoggerFactory.getLogger(ClientAsyncServiceImpl.class);

    @Override
    public String receive(WebRequest webRequest) throws TException {

        logger.info("WebRequest:"+webRequest.toString());

        RequestType type = webRequest.getRequestType();
        final Integer taskId = webRequest.getTaskId();

        RequestReturnBack requestReturnBack = new RequestReturnBack();
        requestReturnBack.setTaskId(taskId);
        String returnStr = null;

        switch (type){
            case OFFLINE_DATA_UPLOAD:
                Parameter<OfflineParam> parameterBean = JSON.parseObject(webRequest.parameter, new TypeReference<Parameter<OfflineParam>>(){});
                OfflineParam offlineParam = (OfflineParam) parameterBean.getParam();
                System.out.println("Here is file upload task...");
                String path = offlineParam.getPath();
                String topic = offlineParam.getTopic();
                FileHandle.scannerReadFile(topic,offlineParam, path);
                logger.info("path: " + path );
                logger.info(JSON.toJSONString(parameterBean) );
                break;

            case DATABASE_DATA_UPLOAD:
                System.out.println("Here is database upload task...");

                try {
                    Thread.sleep(6000);
                    System.out.println("database test");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "num"+webRequest.getTaskId();
//                Parameter<RelationParam> param = JSON.parseObject(webRequest.parameter, new TypeReference<Parameter<RelationParam>>(){});
//                RelationParam dbParam = (RelationParam) param.getParam();
//                String url = dbParam.getDbName();
//                String username = dbParam.getUsername();
//                String password = dbParam.getPassword();
//                if (dbParam.getIsWholeDb()==0){
//                    DbHandler.getAllDbData(DbConstant.MYSQL, dbParam);
//                }else{
//                    String tablename = dbParam.getTableName();
//                    if (dbParam.getIsWholeTable()==1)
//                    {
//                        logger.info("url: "+url+" username: "+username+" password: "+password );
//                        DbHandler.getTableData(DbConstant.MYSQL,tablename,dbParam);
//                        logger.info("data processing and uploading");
//                    }
//                }
//                break;
            case FILELIST_VIEW:
                AsyncTaskUtils.INSTANCE.dispatchNormalTask(new FileListViewTask());
                logger.info("\nTurn to FileList View task executor");
                break;
            case OFFline_File_PREIVEW:
                AsyncTaskUtils.INSTANCE.dispatchNormalTask(new FilePreviewTask());
                logger.info("\nTurn to File Preview task executor");
                break;
            case GET_LIVE_CLIENTS:
                AsyncTaskUtils.INSTANCE.dispatchNormalTask(new GetLiveClients());
                logger.info("\nTurn to Get Live Clients task executor");
                break;
            default:
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.error("Unrecognized Request type");
        }
        return "null";
    }
}
