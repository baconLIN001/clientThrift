package com.bacon.client.core.base;

import com.bacon.client.common.spi.Code;

/**
 * Created by bacon on 2017/4/7.
 */
public enum FileUploadCode implements Code{
    UPLOAD_SUCCESS(0, "上传成功"),
    FILE_NOT_FOUND(1, "文件路径不存在"),
    PARAMETER_INVALID(2, "参数设置不正确"),
    FILE_IOEXCEPTION(3, "文件IO异常"),
    UPLOAD_ERROR(4, "文件上传出错")
    ;

    private Integer code;
    private String description;
    private FileUploadCode(Integer code, String description)
    {
        this.code = code;
        this.description = description;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
