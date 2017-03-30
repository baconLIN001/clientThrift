package com.bacon.client.task.callable;

import com.bacon.client.pojo.Parameter;
import com.bacon.client.pojo.ReturnBack;
import com.bacon.client.utils.FileUtils;
import com.bacon.client.utils.JsonHandleUtils;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.concurrent.Callable;

/**
 * Created by bacon on 2017/3/29.
 */
public class FileUploadCallableTask implements Callable {
    Logger logger = Logger.getLogger(FileUploadCallableTask.class);

    private String info;
    private Parameter parameter;

    private Integer taskId;

    public FileUploadCallableTask(String request_info)
    {
        this.info = request_info;
    }
    public FileUploadCallableTask(Parameter parameter)
    {
        this.parameter = parameter;
    }
    public FileUploadCallableTask(Integer taskId, Parameter parameter){
        this.taskId = taskId;
        this.parameter=parameter;
    }

    @Override
    public ReturnBack call() throws Exception {
        logger.info("Here is file upload task...");

        try {
            Thread.sleep(5000);
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        ReturnBack returnBack = new ReturnBack();

        logger.info("The parameter: " + JsonHandleUtils.parameterBeanToJson(parameter));
        String path = parameter.getPath();
        File file = new File(path);
        String filename = file.getName();
        logger.info("\nfilename: " + filename);

//        bufferReadFile(parameter,path);
        FileUtils.scannerReadFile(parameter,path);

        logger.info("Task " + taskId + " work completely");
        returnBack.setTaskId(taskId);
        returnBack.setReturnInfo("Success");
        return returnBack;
    }
}
