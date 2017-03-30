package com.bacon.client.task.httpTasks;

import com.bacon.client.pojo.ReturnBack;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by bacon on 2017/3/28.
 */
public class HttpReturnBackTask implements Runnable {
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
        String app_ip = properties.getProperty("app_ip");
        try {
            URL url = new URL(app_ip);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            OutputStream outputStream = connection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);

            outputStreamWriter.write(returnBackInfo.toString());
            outputStreamWriter.flush();

            if (connection.getResponseCode() >= 300){
                throw new Exception("HTTP Request is not success, Response code is" +
                connection.getResponseCode());
            }

            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line=bufferedReader.readLine())!=null){
                System.out.println(line);
            }

            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
