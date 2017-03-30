package com.bacon.client.thrift;

import com.bacon.client.sevice.ClientService;
import com.bacon.client.sevice.impl.ClientServiceImpl;
import com.bacon.client.utils.AsyncTaskUtils;
import org.apache.log4j.Logger;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

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
