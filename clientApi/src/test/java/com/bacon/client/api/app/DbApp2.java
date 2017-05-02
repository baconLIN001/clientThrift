package com.bacon.client.api.app;

import com.alibaba.fastjson.JSON;
import com.bacon.client.common.entity.Parameter;
import com.bacon.client.common.entity.RelationParam;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by bacon on 2017/4/24.
 */
public class DbApp2 {
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
        RelationParam relationParam = new RelationParam();
        relationParam.setDbName("jdbc:mysql://125.216.243.200:3306/score");
        relationParam.setJdbc("mysql");
//        relationParam.setTableName("user_score");
        relationParam.setTopic("testDb");
        relationParam.setUsername("root");
        relationParam.setPassword("1234");
//        relationParam.setIsPart(0);
        relationParam.setIsWholeDb(0);
        relationParam.setIsWholeTable(0);
        parameter.setParam(relationParam);
        String parameterJson = JSON.toJSONString(parameter);
        System.out.println(parameterJson);
        WebRequest request = new WebRequest().setRequestType(RequestType.DATABASE_DATA_UPLOAD)
                .setTaskId(5)
                .setParameter(parameterJson);
        try{
            String result = client.receive(request);
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
