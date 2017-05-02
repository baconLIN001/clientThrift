package com.bacon.client.core.callable;

import com.bacon.client.common.entity.OfflineParam;
import com.bacon.client.common.entity.Parameter;
import com.bacon.client.common.util.AsyncTaskUtils;
import com.bacon.client.common.util.FileUtils;
import com.bacon.client.core.base.FileUploadCode;
import com.bacon.client.core.element.CompleteReturnBack;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by bacon on 2017/4/6.
 */
public class FileListUploadCallableTask implements Callable<List<CompleteReturnBack>> {

    Logger logger = Logger.getLogger(FileListUploadCallableTask.class);

    private List<String> filesList;
    private Parameter parameter;
    private Integer taskId;
    private OfflineParam offlineParam;
    private String topic;

    public FileListUploadCallableTask(List<String> filesList, Parameter parameter, Integer taskId) {
        this.filesList = filesList;
        this.parameter = parameter;
        this.taskId = taskId;
    }

    public FileListUploadCallableTask(List<String> filesList, OfflineParam offlineParam, Integer taskId, String topic){
        this.filesList = filesList;
        this.offlineParam = offlineParam;
        this.taskId = taskId;
        this.topic = topic;
    }

    @Override
    public List<CompleteReturnBack> call() throws Exception {
        //对传入的文件列表按小到大排序
        FileUtils.sortFileList(filesList,true);
        List<FutureTask<CompleteReturnBack>> taskList = new ArrayList<FutureTask<CompleteReturnBack>>();
        List<CompleteReturnBack> completeReturnBackList = new ArrayList<>();

        //对每个path创建一个异步线程，并交由线程池运行
        for (int i=0;i<filesList.size();i++)
        {
            FutureTask<CompleteReturnBack> futureTask = new FutureTask<CompleteReturnBack>(
                    new FileUploadCallableTask(taskId,topic,offlineParam,filesList.get(i)));
            taskList.add(futureTask);
            AsyncTaskUtils.INSTANCE.dispatchNormalTask(futureTask);
        }
        logger.info("所有文件任务提交完成，线程池正在运行...");
        for (int i = 0; i < taskList.size(); i++)
        {
            FutureTask<CompleteReturnBack> futureTask = taskList.get(i);
            try {
                CompleteReturnBack completeReturnBack = futureTask.get();
                logger.info(completeReturnBack.getTaskId() + "   file " + i + "   " + completeReturnBack.getFileUploadCode().getDescription());
                completeReturnBackList.add(completeReturnBack);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }catch (ExecutionException e){
                e.printStackTrace();
            }
        }
        return completeReturnBackList;
    }
}
