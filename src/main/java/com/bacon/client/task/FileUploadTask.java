package com.bacon.client.task;

import com.bacon.client.pojo.Parameter;
import com.bacon.client.utils.FileUtils;
import com.bacon.client.utils.JsonHandleUtils;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by bacon on 2017/3/17.
 */
public class FileUploadTask implements Runnable{

        Logger logger = Logger.getLogger(FileUploadTask.class);

    private String info;
    private Parameter parameter;

    public FileUploadTask(String request_info)
    {
        this.info = request_info;
    }
    public FileUploadTask(Parameter parameter)
    {
        this.parameter = parameter;
    }

    @Override
    public void run() {
        logger.info("Here is file upload task...");

//        System.out.println("the info : " + info);
        logger.info("The parameter: " + JsonHandleUtils.parameterBeanToJson(parameter));
        String path = parameter.getPath();
        File file = new File(path);
        String filename = file.getName();
        logger.info("\nfilename: " + filename);

//        bufferReadFile(parameter,path);
        FileUtils.scannerReadFile(parameter,path);

    }

}
