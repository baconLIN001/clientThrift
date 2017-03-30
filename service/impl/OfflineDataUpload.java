package com.bacon.clientThrift.service.impl;

import com.bacon.clientThrift.entity.DataUploadRequest;

/**
 * Created by bacon on 2017/3/17.
 */
public class OfflineDataUpload implements Runnable {

    private DataUploadRequest dataUploadRequest;

    public OfflineDataUpload(DataUploadRequest dataUploadRequest)
    {
        this.dataUploadRequest = dataUploadRequest;
    }

    @Override
    public void run() {
        System.out.println("Executing offlinedataupload task: \nPath:" + dataUploadRequest.getPath()
            +"\nSecurity Level: " + dataUploadRequest.getSecurityLevel());

    }
}
