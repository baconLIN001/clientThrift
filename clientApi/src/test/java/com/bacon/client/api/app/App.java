package com.bacon.client.api.app;

import com.alibaba.fastjson.JSON;
import com.bacon.client.api.pojo.RequestReturnBack;
import com.bacon.client.common.entity.Parameter;
import com.bacon.client.common.entity.RequestType;
import com.bacon.client.common.entity.WebRequest;
import com.bacon.client.common.exception.RequestException;
import com.bacon.client.common.service.ClientService;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.io.*;
import java.util.Properties;

/**
 * Created by bacon on 2017/3/17.
 */
public class App {
    public static void main(String[] args){
        Logger logger = Logger.getLogger(App.class);
        TTransport transport = null;
        Properties properties = new Properties();
        try{
            InputStream inputStream = new BufferedInputStream(new FileInputStream("server.properties"));
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String server_ip = properties.getProperty("server_ip");
        int port = Integer.parseInt(properties.getProperty("port"));
        logger.info("aim server ip : " + server_ip +"\naim server port : " + port);

        transport = new TSocket(server_ip,port,30000);

        TProtocol protocol = new TBinaryProtocol(transport);

        ClientService.Client client = new ClientService.Client(protocol);
        try{
            transport.open();
        } catch (TTransportException e) {
            e.printStackTrace();
        }

        Parameter parameter = new Parameter();
        parameter.setPath("G:\\大三下\\Message Systems\\文档\\test");
        parameter.setAESPriKey("1234567821676878");
        parameter.setSecurityLevel(2);
        parameter.setTopic("test");
        parameter.setIsAllString(true);
        String parameterJson = JSON.toJSONString(parameter);
        System.out.println(parameterJson);
        WebRequest request = new WebRequest().setRequestType(RequestType.OFFLINE_DATA_UPLOAD)
                .setTaskId(2)
                .setParameter(parameterJson);
        try{
            String result = client.receive(request);
            RequestReturnBack requestReturnBack = JSON.parseObject(result,RequestReturnBack.class);
            System.out.println("the scama: " + requestReturnBack.getSchema());
            logger.info(result);
        } catch (RequestException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            if (transport!=null){
                transport.close();
            }
        }
    }
}