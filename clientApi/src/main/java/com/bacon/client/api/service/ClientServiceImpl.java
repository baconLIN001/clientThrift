package com.bacon.client.api.service;

import com.alibaba.fastjson.JSON;
import com.b3434.Factory.ProcessorFactory;
import com.bacon.client.api.http.HttpReturnBackTask;
import com.bacon.client.common.util.FileUtils;
import com.bacon.client.core.base.FileUploadCode;
import com.bacon.client.core.callable.FileListUploadCallableTask;
import com.bacon.client.core.element.CompleteReturnBack;
import com.bacon.client.api.pojo.RequestReturnBack;
import com.bacon.client.common.entity.Parameter;
import com.bacon.client.common.entity.RequestType;
import com.bacon.client.common.entity.WebRequest;
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
        RequestType type = webRequest.getRequestType();
        RequestReturnBack requestReturnBack = new RequestReturnBack();
        String returnStr = null;

        final Integer taskId = webRequest.getTaskId();
        Parameter parameterBean = JSON.parseObject(webRequest.parameter, Parameter.class);

        switch (type){
            case OFFLINE_DATA_UPLOAD:
                String filePath = parameterBean.getPath();
                requestReturnBack.setFolder(FileUtils.checkIsFolder(filePath));

                com.b3434.Bean.Parameter parameter = JSON.parseObject(webRequest.parameter,com.b3434.Bean.Parameter.class);
                requestReturnBack.setSchema(ProcessorFactory.createOfflineSchema(parameter));

                long totalSize = 0;
                //如果路径是文件
                if (!FileUtils.checkIsFolder(filePath))
                {
                    File file = new File(filePath);
                    long fileSize = file.length();
                    totalSize = fileSize;
                    String md5 = FileUtils.getFileMD5(filePath);
                    requestReturnBack.setMd5(md5);

                    //新建file upload的callable task
                    FileUploadCallableTask fileUploadCallableTask = new FileUploadCallableTask(taskId, parameterBean,filePath);

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

                    List<Integer>sizes = new ArrayList<>();
                    List<String> md5List = new ArrayList<>();
                    List<String> files = new ArrayList<>();
                    for (String path : fileList) {
                        File file = new File(path);
                        long size = file.length();
                        sizes.add((int) size);
                        totalSize += size;

                        String md5 = FileUtils.getFileMD5(path);
                        md5List.add(md5);
                        files.add(path);
                    }
                    requestReturnBack.setFileSize(sizes);
                    requestReturnBack.setMd5List(md5List);
                    requestReturnBack.setFileList(files);

                    //将文件大小递增排序
                    FileUtils.sortFileList(fileList,true);
                    //新建file list的文件上传callable task
                    FileListUploadCallableTask fileListUploadCallableTask = new FileListUploadCallableTask(fileList,parameterBean,taskId);

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
                requestReturnBack.setTotalSize(totalSize);
                returnStr = JSON.toJSONString(requestReturnBack);
                logger.info("return " + returnStr + " to request");
                break;



            case DATABASE_DATA_UPLOAD:
                AsyncTaskUtils.INSTANCE.dispatchNormalTask(new DatabaseUploadTask());
                logger.info("\nTurn to Database Upload task executor");
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
