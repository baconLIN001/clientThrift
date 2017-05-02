package com.bacon.client.api.pojo;

/**
 * Created by bacon on 2017/4/28.
 */
public class DbReturnBack {
    //单个表的返回属性
    private String schema;
    private String tableName;

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
