package com.bacon.client.task.httpTasks;

import com.bacon.client.utils.HttpUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 * Created by bacon on 2017/3/30.
 */
public class HttpGetAliveTask implements Runnable {
    Logger logger = Logger.getLogger(HttpGetAliveTask.class);

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
        String app_url = properties.getProperty("app_alive_url");
        while (true){
            logger.info("send get alive request: "+app_url);
            String response = HttpUtils.httpGet(app_url);
            logger.info("get alive response: "+response);
            try {
                Thread.sleep(600000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
