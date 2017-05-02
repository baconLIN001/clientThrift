package com.bacon.client.api.asyncThrift;

import com.alibaba.fastjson.JSON;
import com.bacon.client.common.entity.OfflineParam;
import com.bacon.client.common.entity.Parameter;
import com.bacon.client.common.entity.RequestType;
import com.bacon.client.common.entity.WebRequest;
import com.bacon.client.common.service.ClientService;
import com.bacon.client.core.asyncCallback.DbCallback;
import com.bacon.client.core.asyncCallback.FileCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bacon on 2017/4/25.
 */
public class asyncFileApp {
    public static final String address = "127.0.0.1";
    public static final int port = 8989;
    public static final int clientTimeout = 30000;

    public static void main_asy() throws Exception {
        try {
            TAsyncClientManager clientManager = new TAsyncClientManager();
            TNonblockingTransport transport = new TNonblockingSocket(address, port, clientTimeout);
            TProtocolFactory protocol = new TCompactProtocol.Factory();
            ClientService.AsyncClient asyncClient = new ClientService.AsyncClient(protocol, clientManager, transport);
            System.out.println("Client calls .....");
            FileCallback callBack = new FileCallback();

            Parameter parameter = new Parameter();
            OfflineParam offlineParam = new OfflineParam();

            offlineParam.setPath("G:\\Work\\Message Systems\\Doc\\test\\test.txt");
            offlineParam.setAESPriKey("123456");
            offlineParam.setSecurityLevel(2);
            offlineParam.setTopic("test");
            parameter.setParam(offlineParam);
            String parameterJson = JSON.toJSONString(parameter);
            WebRequest request = new WebRequest().setRequestType(RequestType.OFFLINE_DATA_UPLOAD)
                    .setTaskId(2)
                    .setParameter(parameterJson);

            asyncClient.receive(request, callBack);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
//        for (int i =1; i<50;i++)
//        {
            main_asy();
//            System.out.println(i);
//            Thread.sleep(500);
//        }
    }
}
