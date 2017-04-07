package com.bacon.client.api.pojo;

import org.apache.avro.Schema;

import java.util.List;

/**
 * Created by bacon on 2017/4/5.
 */
public class RequestReturnBack {
    private Integer taskId;
    private String md5;
    private List<String> md5List;
    private String schema;
    private boolean isFolder;
    private List<String> fileList;
    private long totalSize;
    private List<Integer> fileSize;
    private String Type;

    public RequestReturnBack(){

    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public boolean isFolder() {
        return isFolder;
    }

    public void setFolder(boolean isFolder) {
        this.isFolder = isFolder;
    }

    public List<String> getFileList() {
        return fileList;
    }

    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public List<Integer> getFileSize() {
        return fileSize;
    }

    public void setFileSize(List<Integer> fileSize) {
        this.fileSize = fileSize;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public List<String> getMd5List() {
        return md5List;
    }

    public void setMd5List(List<String> md5List) {
        this.md5List = md5List;
    }
}
