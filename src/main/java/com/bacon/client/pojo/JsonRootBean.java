/**
  * Copyright 2017 bejson.com 
  */
package com.bacon.client.pojo;

/**
 * Auto-generated: 2017-03-22 17:8:44
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class JsonRootBean {

    private String type;
    private String topic;
    private Integer taskId;
    private Parameter parameter;
    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

    public void setTopic(String topic) {
         this.topic = topic;
     }
     public String getTopic() {
         return topic;
     }

    public void setParameter(Parameter parameter) {
         this.parameter = parameter;
     }
     public Parameter getParameter() {
         return parameter;
     }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
}