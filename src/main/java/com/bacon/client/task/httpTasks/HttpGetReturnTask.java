package com.bacon.client.task.httpTasks;

import com.bacon.client.pojo.ReturnBack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by bacon on 2017/3/30.
 */
public class HttpGetReturnTask implements Runnable{
    private Integer taskId;
    private String msg;
    private ReturnBack returnBackInfo;


    @Override
    public void run() {
        Properties properties = new Properties();
        String server_ip = properties.getProperty("server_ip");
        try {
            URL url = new URL(server_ip);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if (connection.getResponseCode() >= 300){
                throw new Exception("HTTP Request is not success, Response code is" +
                        connection.getResponseCode());
            }

            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer result = new StringBuffer();
            String line = null;
            while ((line=bufferedReader.readLine())!=null){
                result.append(line);
            }
            System.out.println(result.toString());
        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
