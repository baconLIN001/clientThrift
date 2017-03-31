package com.bacon.client.utils;

import com.alibaba.fastjson.JSON;
import com.bacon.client.pojo.JsonRootBean;
import com.bacon.client.pojo.Parameter;
import com.bacon.client.pojo.ReturnBack;

import java.util.List;

/**
 * Created by bacon on 2017/3/22.
 */
public class JsonHandleUtils<E> {

    /**
     * 由json字符串解析成请求的javabean
     * @param jsonString
     * @return
     */
    public static JsonRootBean jsonToRequestbean(String jsonString)
    {
        JsonRootBean requestBean = JSON.parseObject(jsonString, JsonRootBean.class);
        return requestBean;
    }

    /**
     * 由json字符串解析成请求的参数的javabean
     * @param jsonString
     * @return
     */
    public static Parameter jsonToParameterbean(String jsonString)
    {
        Parameter parameterBean = JSON.parseObject(jsonString, Parameter.class);
        return parameterBean;
    }
//    public List<E> jsonToList(String jsonString)
//    {
//        List<E> list = JSON.parseArray(jsonString,E.class);
//        return list;
//    }
    /**
     * 由requestBean生成json
     * @param requestBean
     * @return
     */
    public static String requestBeanToJson(JsonRootBean requestBean)
    {
        String jsonString = JSON.toJSONString(requestBean);
        return jsonString;
    }

    /**
     * 由parameterBean生成json
     * @param parameterBean
     * @return
     */
    public static String parameterBeanToJson(Parameter parameterBean)
    {
        String jsonString = JSON.toJSONString(parameterBean);
        return jsonString;
    }

    /**
     * 由list<E>生成json
     * @param list
     * @return
     */
    public String listObjectToJson(List<E> list)
    {
        String jsonString = JSON.toJSONString(list);
        return jsonString;
    }

    public static String returnBackBeanToJson(ReturnBack returnBack)
    {
        String returnBackStr = JSON.toJSONString(returnBack);
        return returnBackStr;
    }

    public static ReturnBack jsonToReturnBack(String returnBackJson)
    {
        ReturnBack returnBackBean = JSON.parseObject(returnBackJson, ReturnBack.class);
        return returnBackBean;
    }
}
