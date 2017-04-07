package com.bacon.client.api.thrift;

import com.bacon.client.common.util.AsyncTaskUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 * Created by bacon on 2017/3/17.
 */
public class RequestReceiver{

    Logger logger = Logger.getLogger(RequestReceiver.class);

    protected RequestReceiver(){}

    private static RequestReceiver instance = new RequestReceiver();

    public static RequestReceiver getInstance(){
        return instance;
    }

    public void initReceiver(){
        Properties properties = new Properties();
        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream("server.properties"));
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int port = Integer.parseInt(properties.getProperty("port"));

        AsyncTaskUtils.INSTANCE.dispatchNormalTask(new RunningServer(port));
    }
}
