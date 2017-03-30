package com.bacon.clientThrift.service.impl;

import com.bacon.clientThrift.entity.DataUploadRequest;
import com.bacon.clientThrift.entity.RequestException;
import com.bacon.clientThrift.entity.RequestType;
import com.bacon.clientThrift.service.DataUploadService;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by bacon on 2017/3/16.
 */
public class DataUploadServiceImpl implements DataUploadService.Iface {

    Logger logger = Logger.getLogger(DataUploadService.class);

    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

    @Override
    public String dataUpload(DataUploadRequest dataUploadRequest) throws RequestException, TException {

        logger.info("\n\nDataType= " + dataUploadRequest.getRequestType() + "\n"
                + "Data path : " + dataUploadRequest.getPath() + "\n"
                + "Security Level : " + dataUploadRequest.getSecurityLevel() +"\n");
        //判定属于什么数据传输类型
        if (dataUploadRequest.getRequestType()== RequestType.OFFLINE_DATA_UPLOAD)
        {

            OfflineDataUpload offlineDataUpload = new OfflineDataUpload(dataUploadRequest);
            fixedThreadPool.execute(offlineDataUpload);
            logger.info("\n\nOfflineDataUpload: executing");
        }else if (dataUploadRequest.getRequestType()==RequestType.STREAM_DATA_UPLOAD)
        {
            StreamDataUpload streamDataUpload = new StreamDataUpload(dataUploadRequest);
            fixedThreadPool.execute(streamDataUpload);
            logger.info("\n\nStreamDataUpload: executing");
        }else {
            logger.info("\n\nError: UnrecognizedDataType");
            return "UnrecognizedDataType";
        }
        return "Running";
    }
}
