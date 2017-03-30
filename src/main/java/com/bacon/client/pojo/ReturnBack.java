package com.bacon.client.pojo;

/**
 * Created by bacon on 2017/3/28.
 */
public class ReturnBack {
    private Integer taskId;
    private String returnInfo;

    public ReturnBack() {
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getReturnInfo() {
        return returnInfo;
    }

    public void setReturnInfo(String returnInfo) {
        this.returnInfo = returnInfo;
    }
}
