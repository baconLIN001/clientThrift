package com.bacon.client.core.asyncCallback;

import com.bacon.client.common.exception.RequestException;
import com.bacon.client.common.service.ClientService;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;

/**
 * Created by bacon on 2017/4/24.
 */
public class DbCallback implements AsyncMethodCallback<ClientService.AsyncClient.receive_call> {

    @Override
    public void onComplete(ClientService.AsyncClient.receive_call receive_call) {
        System.out.println("complete");
        try {
            System.out.println("AsynCall result =:" + receive_call.getResult().toString());
        } catch (TException e) {
            e.printStackTrace();
        }

//        try {
//            System.out.println(receive_call.getResult().toString());
//        } catch (RequestException e) {
//            e.printStackTrace();
//        } catch (TException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onError(Exception e) {
        System.out.println("error");
    }
}
