package com.bacon.client.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by bacon on 2017/3/30.
 */
public class HttpUtils {
    Logger logger = Logger.getLogger(HttpUtils.class);

    public static String httpPost(String url, String param)
    {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String result = null;
        JSONObject jsonResult = null;
        HttpPost method = new HttpPost(url);
        try{
            if (null!=param)
            {
                StringEntity entity = new StringEntity(param.toString(),"utf-8");
                entity.setContentEncoding("UTF-8");
                method.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(method);
            url = URLDecoder.decode(url,"UTF-8");
            if (response.getStatusLine().getStatusCode()==200){
                String str = "";
                try {
                    str = EntityUtils.toString(response.getEntity());
                    result = str;
//                    jsonResult = JSONObject.fromObject(str);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                System.out.println("请求提交失败"+url+"\n返回码: " + response.getStatusLine().getStatusCode());
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String httpGet(String url){
        String result = null;
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String strResult = EntityUtils.toString(response.getEntity());
                result = strResult;
            }
            else {
                System.out.println("请求提交失败"+url);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
