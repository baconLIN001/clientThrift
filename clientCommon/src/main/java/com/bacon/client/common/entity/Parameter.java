package com.bacon.client.common.entity;

/**
 * Created by Lee on 2017/4/25 0025.
 */
public class Parameter<T> {
    private String type;

    private T param;

    public String getType() {
        return type;
    }

    public Parameter setType(String type) {
        this.type = type;
        return this;
    }

    public T getParam() {
        return param;
    }

    public Parameter setParam(T param) {
        this.param = param;
        return this;
    }
}
