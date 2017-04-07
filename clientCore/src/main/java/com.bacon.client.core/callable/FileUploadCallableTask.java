package com.bacon.client.core.callable;

import com.alibaba.fastjson.JSON;
import com.bacon.client.core.base.FileUploadCode;
import com.bacon.client.core.element.CompleteReturnBack;
import com.bacon.client.common.entity.Parameter;
import com.bacon.client.core.avro.FileHandle;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.concurrent.Callable;

/**
 * Created by bacon on 2017/3/29.
 */
public class FileUploadCallableTask implements Callable<CompleteReturnBack> {
    Logger logger = Logger.getLogger(FileUploadCallableTask.class);

    private Parameter parameter;
    private String path;
    private Integer taskId;

    public FileUploadCallableTask(Parameter parameter)
    {
        this.parameter = parameter;
    }
    public FileUploadCallableTask(Integer taskId, Parameter parameter, String path){
        this.taskId = taskId;
        this.parameter=parameter;
        this.path = path;
    }

    @Override
    public CompleteReturnBack call() throws Exception {
        logger.info("Here is file upload task...");

        CompleteReturnBack completeReturnBack = new CompleteReturnBack(taskId);

        logger.info("The parameter: " + JSON.toJSONString(parameter));
        File file = new File(path);
        String filename = file.getName();
        logger.info("\nfilename: " + filename);

        FileUploadCode fileUploadCode = FileHandle.scannerReadFile(parameter, path);
        completeReturnBack.setPath(path);
        completeReturnBack.setFileUploadCode(fileUploadCode);

        logger.info("Task " + taskId + "   " + filename + "  " + " upload completely");
        completeReturnBack.setReturnInfo("Success");
        return completeReturnBack;
    }
}
