package com.bacon.client.api.pojo;

import com.bacon.client.common.entity.RequestType;
import org.apache.avro.Schema;

import java.util.List;

/**
 * Created by bacon on 2017/4/5.
 */
public class RequestReturnBack<T> {

    private String requestType;//请求类型
    private Integer taskId;

    private int multi;//是否多表或多文件
    private T returnInfo;//
    private List<T> returnInfoList;

    //file的返回属性
    private long totalSize;

    public RequestReturnBack(){

    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public T getReturnInfo() {
        return returnInfo;
    }

    public RequestReturnBack setReturnInfo(T returnInfo) {
        this.returnInfo = returnInfo;
        return this;
    }

    public int getMulti() {
        return multi;
    }

    public void setMulti(int multi) {
        this.multi = multi;
    }

    public List<T> getReturnInfoList() {
        return returnInfoList;
    }

    public void setReturnInfoList(List<T> returnInfoList) {
        this.returnInfoList = returnInfoList;
    }
}
