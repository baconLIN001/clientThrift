package com.bacon.clientThrift.service.impl;

import com.bacon.clientThrift.entity.DataUploadRequest;

/**
 * Created by bacon on 2017/3/17.
 */
public class StreamDataUpload implements Runnable {

    private DataUploadRequest dataUploadRequest;

    public StreamDataUpload (DataUploadRequest dataUploadRequest)
    {
        this.dataUploadRequest = dataUploadRequest;
    }

    @Override
    public void run() {

    }
}
