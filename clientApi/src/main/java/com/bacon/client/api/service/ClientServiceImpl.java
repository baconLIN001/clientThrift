package com.bacon.client.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.b3434.Factory.ProcessorFactory;
import com.bacon.client.api.http.HttpReturnBackTask;
import com.bacon.client.api.pojo.DbReturnBack;
import com.bacon.client.api.pojo.FileReturnBack;
import com.bacon.client.common.entity.*;
import com.bacon.client.common.util.FileUtils;
import com.bacon.client.core.avro.DbHandler;
import com.bacon.client.core.base.FileUploadCode;
import com.bacon.client.core.callable.DatabaseUploadCallableTask;
import com.bacon.client.core.callable.FileListUploadCallableTask;
import com.bacon.client.core.element.CompleteReturnBack;
import com.bacon.client.api.pojo.RequestReturnBack;
import com.bacon.client.common.exception.RequestException;
import com.bacon.client.common.service.ClientService;
import com.bacon.client.common.util.AsyncTaskUtils;
import com.bacon.client.core.callable.FileUploadCallableTask;
import com.bacon.client.core.runnable.DatabaseUploadTask;
import com.bacon.client.core.runnable.FileListViewTask;
import com.bacon.client.core.runnable.FilePreviewTask;
import com.bacon.client.core.runnable.GetLiveClients;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.TProcessorFactory;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by bacon on 2017/3/17.
 */
public class ClientServiceImpl implements ClientService.Iface{

    Logger logger = Logger.getLogger(ClientService.class);


    @Override
    public String receive(final WebRequest webRequest) throws RequestException, TException {

        logger.info("WebRequest:"+webRequest.toString());
        RequestType type = webRequest.getRequestType();
        RequestReturnBack requestReturnBack = new RequestReturnBack();
        String returnStr = null;

        final Integer taskId = webRequest.getTaskId();
        requestReturnBack.setTaskId(taskId);
        requestReturnBack.setMulti(1);//默认不是多文件或多表

        switch (type){
            case OFFLINE_DATA_UPLOAD:
                //paramter转化成file的parameter
                Parameter parameterBean = JSON.parseObject(webRequest.parameter, new TypeReference<Parameter<OfflineParam>>(){});
                OfflineParam offlineParam = (OfflineParam) parameterBean.getParam();

                String topic = ((OfflineParam)parameterBean.getParam()).getTopic();
                String filePath = offlineParam.getPath();

                //设置thrift的返回
                if (FileUtils.checkIsFolder(filePath))
                {
                    requestReturnBack.setMulti(0);
                }

                com.b3434.Bean.Parameter parameter = JSON.parseObject(webRequest.parameter,com.b3434.Bean.Parameter.class);
                String fileSchema = ProcessorFactory.createOfflineSchema(parameter);

                long totalSize = 0;
                //如果路径是文件
                if (!FileUtils.checkIsFolder(filePath))
                {
                    File file = new File(filePath);
                    long fileSize = file.length();
                    totalSize = fileSize;

                    //设置单个file的返回
                    FileReturnBack fileReturnBack = new FileReturnBack();
                    String md5 = FileUtils.getFileMD5(filePath);
                    fileReturnBack.setMd5(md5);
                    fileReturnBack.setSchema(fileSchema);
                    fileReturnBack.setFileName(filePath);
                    fileReturnBack.setFileSize(fileSize);

                    requestReturnBack.setReturnInfo(fileReturnBack);

                    //新建file upload的callable task
                    FileUploadCallableTask fileUploadCallableTask = new FileUploadCallableTask(taskId, topic,offlineParam,filePath);

                    logger.info("\nTurn to File Upload task executor.....");
                    //使用future task进行回调
                    FutureTask<CompleteReturnBack> futureTask = new FutureTask<CompleteReturnBack>(fileUploadCallableTask){
                        //异步任务执行完成，进行回调
                        @Override
                        protected void done(){
                            //获取future task返回值
                            CompleteReturnBack completeReturnBack = null;
                            try {
                                //阻塞，等待异步任务执行完毕-获取异步任务的返回值
                                completeReturnBack = get();

                                logger.info("TaskId: " + completeReturnBack.getTaskId() + "    Info: " + completeReturnBack.getReturnInfo());
                                //http发送task运行完成消息，异步
                                HttpReturnBackTask httpReturnBackTask = new HttpReturnBackTask(completeReturnBack);
                                AsyncTaskUtils.INSTANCE.dispatchNormalTask(httpReturnBackTask);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    //异步运行future task
                    AsyncTaskUtils.INSTANCE.dispatchNormalTask(futureTask);
                    logger.info("\nExecuting file future task.....");

                    //设置thrift返回内容
                }
                //路径是文件夹
                else {
                    //获取路径文件夹下所有文件
                    List<String> fileList = new ArrayList<>();
                    fileList = FileUtils.getAllFiles(filePath);
                    List<FileReturnBack> fileReturnBackList = new ArrayList<>();
                    for (String path : fileList) {


                        File file = new File(path);
                        long size = file.length();
                        totalSize += size;

                        String md5 = FileUtils.getFileMD5(path);
                        //设置每个文件的返回
                        FileReturnBack fileReturnBack = new FileReturnBack();
                        fileReturnBack.setFileName(path);
                        fileReturnBack.setFileSize(size);
                        fileReturnBack.setMd5(md5);
                        fileReturnBack.setSchema(fileSchema);

                        fileReturnBackList.add(fileReturnBack);
                    }
                    requestReturnBack.setTotalSize(totalSize);
                    requestReturnBack.setReturnInfoList(fileReturnBackList);
                    //将文件大小递增排序
                    FileUtils.sortFileList(fileList,true);
                    //新建file list的文件上传callable task
                    FileListUploadCallableTask fileListUploadCallableTask = new FileListUploadCallableTask(fileList,offlineParam,taskId,topic);

                    logger.info("\nTurn to File list upload task executor.....");
                    FutureTask<List<CompleteReturnBack>> futureTask = new FutureTask<List<CompleteReturnBack>>(fileListUploadCallableTask){
                        //异步任务执行完成，进行回调
                        @Override
                        protected void done(){
                            List<CompleteReturnBack> completeReturnBackList = null;
                            try {
                                completeReturnBackList = get();
                                Integer taskId = completeReturnBackList.get(0).getTaskId();
                                CompleteReturnBack completeReturnBackFinal = new CompleteReturnBack(taskId);
                                completeReturnBackFinal.setFileUploadCode(FileUploadCode.UPLOAD_SUCCESS);

                                for (CompleteReturnBack completeReturnBack : completeReturnBackList){
                                    logger.info("TaskId: " + completeReturnBack.getTaskId() + "   " + completeReturnBack.getPath() + "   " + completeReturnBack.getReturnInfo());
                                    if (completeReturnBack.getFileUploadCode()!= FileUploadCode.UPLOAD_SUCCESS){
                                        completeReturnBackFinal.setFileUploadCode(FileUploadCode.UPLOAD_ERROR);
                                        List<String> errorPathList = completeReturnBackFinal.getErrorPaths();
                                        errorPathList.add(completeReturnBack.getPath());
                                        completeReturnBackFinal.setErrorPaths(errorPathList);
                                    }
                                }
                                HttpReturnBackTask httpReturnBackTask = new HttpReturnBackTask(completeReturnBackFinal);
                                logger.info("completeReturnBackFinal: "+JSON.toJSONString(completeReturnBackFinal));
                                AsyncTaskUtils.INSTANCE.dispatchNormalTask(httpReturnBackTask);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    //使用线程池运行future task
                    AsyncTaskUtils.INSTANCE.dispatchNormalTask(futureTask);
                    logger.info("\nExecuting file list future task.....");

                }
                returnStr = JSON.toJSONString(requestReturnBack);
                logger.info("return " + returnStr + " to client");
                break;



            case DATABASE_DATA_UPLOAD:
                //paramter转化和提取
                Parameter<RelationParam> param = JSON.parseObject(webRequest.parameter, new TypeReference<Parameter<RelationParam>>(){});
                RelationParam dbParam = (RelationParam) param.getParam();

                //设置thrift 的 request return back
                requestReturnBack.setRequestType("database");

                //整个库，多表
                if (dbParam.getIsWholeDb()==0){
                    requestReturnBack.setMulti(0);
                    List<DbReturnBack> dbReturnBackList = new ArrayList<>();//返回信息列表
                    try {
                        List<String> dbTableNameList = DbHandler.getDbTables(dbParam.getJdbc(),dbParam.getDbName(),dbParam.getUsername(),dbParam.getPassword());//库内所有表
                        for (String table : dbTableNameList){
                            //每个表一条返回信息
                            DbReturnBack dbReturnBack = new DbReturnBack();

                            String dbSchema = DbHandler.getTableSchema(dbParam.getJdbc(),table,JSON.toJSONString(dbParam));
                            dbReturnBack.setTableName(table);
                            dbReturnBack.setSchema(dbSchema);

                            dbReturnBackList.add(dbReturnBack);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    //设置整个返回列表
                    requestReturnBack.setReturnInfoList(dbReturnBackList);
                }else {
                    //数据库单表
                    String schema = DbHandler.getTableSchema(dbParam.getJdbc(),dbParam.getTableName(),JSON.toJSONString(dbParam));
                    //数据单表返回
                    DbReturnBack dbReturnBack = new DbReturnBack();
                    dbReturnBack.setTableName(dbParam.getTableName());
                    dbReturnBack.setSchema(schema);

                    requestReturnBack.setReturnInfo(dbReturnBack);
                }
                //新建file list的文件上传callable task
                DatabaseUploadCallableTask databaseUploadCallableTask = new DatabaseUploadCallableTask(dbParam);

                logger.info("\nTurn to db upload task executor.....");
                FutureTask<CompleteReturnBack> futureTask = new FutureTask<CompleteReturnBack>(databaseUploadCallableTask){
                    //异步任务执行完成，进行回调
                    @Override
                    protected void done(){
                        //获取future task返回值
                        CompleteReturnBack completeReturnBack = null;
                        try {
                            //阻塞，等待异步任务执行完毕-获取异步任务的返回值
                            completeReturnBack = get();
                            completeReturnBack.setTaskId(taskId);

                            logger.info("TaskId: " + taskId +" dbUploadCode" + completeReturnBack.getDbUploadCode()+ " Info: " + completeReturnBack.getReturnInfo());
                            //http发送task运行完成消息，异步
                            HttpReturnBackTask httpReturnBackTask = new HttpReturnBackTask(completeReturnBack);
                            AsyncTaskUtils.INSTANCE.dispatchNormalTask(httpReturnBackTask);
                            logger.info("complete return: " + JSON.toJSONString(completeReturnBack));

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                };
                AsyncTaskUtils.INSTANCE.dispatchNormalTask(futureTask);
                returnStr = JSON.toJSONString(requestReturnBack);
                logger.info("\nTurn to Database Upload task executor");
                logger.info("return " + returnStr + " to client");
                break;
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
                logger.error("Unrecognized Request type");
                return "Unrecognized Request type";
        }
        return returnStr;
    }
}
