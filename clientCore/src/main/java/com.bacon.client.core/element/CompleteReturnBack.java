package com.bacon.client.core.element;

import com.bacon.client.core.base.FileUploadCode;

import java.util.List;

/**
 * Created by bacon on 2017/3/28.
 */
public class CompleteReturnBack {
    private Integer taskId;
    private String path;
    private FileUploadCode fileUploadCode;
    private String returnInfo;
    private List<String> errorPaths;

    public CompleteReturnBack() {
    }
    public CompleteReturnBack(Integer taskId)
    {
        this.taskId = taskId;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public FileUploadCode getFileUploadCode() {
        return fileUploadCode;
    }

    public void setFileUploadCode(FileUploadCode fileUploadCode) {
        this.fileUploadCode = fileUploadCode;
    }

    public List<String> getErrorPaths() {
        return errorPaths;
    }

    public void setErrorPaths(List<String> errorPaths) {
        this.errorPaths = errorPaths;
    }
}
