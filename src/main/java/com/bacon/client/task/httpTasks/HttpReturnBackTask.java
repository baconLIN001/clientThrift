package com.bacon.client.task.httpTasks;

import com.bacon.client.pojo.ReturnBack;
import com.bacon.client.utils.HttpUtils;
import com.bacon.client.utils.JsonHandleUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by bacon on 2017/3/28.
 */
public class HttpReturnBackTask implements Runnable {
    Logger logger = Logger.getLogger(HttpReturnBackTask.class);

    private Integer taskId;
    private String msg;
    private ReturnBack returnBackInfo;
    public HttpReturnBackTask(ReturnBack returnBack)
    {
        this.returnBackInfo = returnBack;
    }

    public HttpReturnBackTask(Integer taskId, String msg)
    {
        returnBackInfo.setTaskId(taskId);
        returnBackInfo.setReturnInfo(msg);
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
        String returnBackJsonStr = JsonHandleUtils.returnBackBeanToJson(returnBackInfo);
        String response = HttpUtils.httpPost(app_url,returnBackJsonStr);
        logger.info("after send task complete meg, response: "+response);
//        try {
//            URL url = new URL(app_url);
//            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
//            OutputStream outputStream = connection.getOutputStream();
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
//
//            outputStreamWriter.write(returnBackInfo.toString());
//            outputStreamWriter.flush();
//
//            if (connection.getResponseCode() >= 300){
//                throw new Exception("HTTP Request is not success, Response code is" +
//                connection.getResponseCode());
//            }
//
//            InputStream inputStream = connection.getInputStream();
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            String line = null;
//            while ((line=bufferedReader.readLine())!=null){
//                System.out.println(line);
//            }
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
