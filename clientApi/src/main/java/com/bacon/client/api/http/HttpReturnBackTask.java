package com.bacon.client.api.http;

import com.alibaba.fastjson.JSON;
import com.bacon.client.core.element.CompleteReturnBack;
import com.bacon.client.common.util.HttpUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 * Created by bacon on 2017/3/28.
 */
public class HttpReturnBackTask implements Runnable {
    Logger logger = Logger.getLogger(HttpReturnBackTask.class);

    private Integer taskId;
    private String msg;
    private CompleteReturnBack completeReturnBackInfo;
    public HttpReturnBackTask(CompleteReturnBack completeReturnBack)
    {
        this.completeReturnBackInfo = completeReturnBack;
    }

    public HttpReturnBackTask(Integer taskId, String msg)
    {
        completeReturnBackInfo.setTaskId(taskId);
        completeReturnBackInfo.setReturnInfo(msg);
    }

    @Override
    public void run() {
        Properties properties = new Properties();
        try{
            InputStream inputStream = new BufferedInputStream(new FileInputStream("app.properties"));
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String app_url = properties.getProperty("app_complete_url");
        logger.info("send task complete url: " + app_url);
        String returnBackJsonStr = JSON.toJSONString(completeReturnBackInfo);
        String response = HttpUtils.httpPost(app_url, returnBackJsonStr);
        logger.info("after send task complete meg, response: "+response);
    }
}
