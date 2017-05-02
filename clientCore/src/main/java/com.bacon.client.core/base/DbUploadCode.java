package com.bacon.client.core.base;

import com.bacon.client.common.spi.Code;

/**
 * Created by bacon on 2017/4/24.
 */
public enum DbUploadCode implements Code{
    UPLOAD_SUCCESS(0,"上传成功"),
    SQL_EXCEPTION(1,"sql错误"),
    URL_ERROR(2,"URL路径错误"),
    DB_NOT_FOUNT(3,"找不到指定数据库"),
    TABLE_NOT_FOUNT(4,"找不到指定数据表"),
    AUTHORITY_ERROR(5,"数据库权限错误"),
    UPLOAD_ERROR(6,"数据库上传错误")
    ;

    private Integer code;
    private String description;

    private DbUploadCode(Integer code, String description){
        this.code = code;
        this.description = description;
    }
    @Override
    public Integer getCode() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
