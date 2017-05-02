package com.bacon.client.api.asyncThrift;

import com.alibaba.fastjson.JSON;
import com.bacon.client.common.entity.Parameter;
import com.bacon.client.common.entity.RelationParam;
import com.bacon.client.common.entity.RequestType;
import com.bacon.client.common.entity.WebRequest;
import com.bacon.client.common.service.ClientService;
import com.bacon.client.core.asyncCallback.DbCallback;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bacon on 2017/4/25.
 */
public class asyncDbApp {
    public static final String address = "127.0.0.1";
    public static final int port = 8989;
    public static final int clientTimeout = 30000;


    public static void main_syn() {
        TTransport transport = new TFramedTransport(new TSocket(address, port, clientTimeout));
        TProtocol protocol = new TCompactProtocol(transport);
        ClientService.Client client = new ClientService.Client(protocol);

        Parameter parameter = new Parameter();
        RelationParam relationParam = new RelationParam();
        relationParam.setDbName("jdbc:mysql://125.216.243.200:3306/score");
        relationParam.setTableName("user_score");
        relationParam.setTopic("testDb");
        relationParam.setUsername("root");
        relationParam.setPassword("1234");
        relationParam.setIsPart(0);
        List<String> whiteFields = new ArrayList<>();
        whiteFields.add("id");
        whiteFields.add("name");
        whiteFields.add("math");
        whiteFields.add("os");
        whiteFields.add("time");
        List<String> blackFields = new ArrayList<>();
        blackFields.add("art");
        blackFields.add("username");
        relationParam.setIsWholeTable(1);
        relationParam.setWhiteList(whiteFields);
        relationParam.setBlackList(blackFields);
        String parameterJson = JSON.toJSONString(parameter);
        System.out.println(parameterJson);
        WebRequest request = new WebRequest().setRequestType(RequestType.DATABASE_DATA_UPLOAD)
                .setTaskId(5)
                .setParameter(parameterJson);

        try {
            transport.open();
            System.out.println(client.receive(request));

        } catch (TApplicationException e) {
            System.out.println(e.getMessage() + " " + e.getType());
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
        transport.close();
    }

    public static void main_asy() throws Exception {
        try {
            TAsyncClientManager clientManager = new TAsyncClientManager();
            TNonblockingTransport transport = new TNonblockingSocket(address, port, clientTimeout);
            TProtocolFactory protocol = new TCompactProtocol.Factory();
            ClientService.AsyncClient asyncClient = new ClientService.AsyncClient(protocol, clientManager, transport);
            System.out.println("Client calls .....");
            DbCallback callBack = new DbCallback();

            Parameter parameter = new Parameter();
            RelationParam relationParam = new RelationParam();
            relationParam.setDbName("jdbc:mysql://125.216.243.200:3306/score");
            relationParam.setTableName("user_score");
            relationParam.setTopic("testDb");
            relationParam.setUsername("root");
            relationParam.setPassword("1234");
            relationParam.setIsPart(0);
            List<String> whiteFields = new ArrayList<>();
            whiteFields.add("id");
            whiteFields.add("name");
            whiteFields.add("math");
            whiteFields.add("os");
            whiteFields.add("time");
            List<String> blackFields = new ArrayList<>();
            blackFields.add("art");
            blackFields.add("username");
            relationParam.setIsWholeTable(1);
            relationParam.setWhiteList(whiteFields);
            relationParam.setBlackList(blackFields);
            parameter.setParam(relationParam);
            String parameterJson = JSON.toJSONString(parameter);
            System.out.println(parameterJson);
            WebRequest request = new WebRequest().setRequestType(RequestType.DATABASE_DATA_UPLOAD)
                    .setTaskId(5)
                    .setParameter(parameterJson);


            asyncClient.receive(request, callBack);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        for (int i =1; i<30;i++)
        {
            main_asy();
            System.out.println(i);
            Thread.sleep(1000);
        }
    }
}
