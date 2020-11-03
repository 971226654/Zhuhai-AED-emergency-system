package com.bnuz.aed.common.tools.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import cn.hutool.json.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * @author Leia Liang
 * @date 2020/9/16
 * 处理Get和Post请求
 * 返回值为响应的json字符串
 */
public class HttpsClientUtils {

    /**
     * @param url 发送请求的URL
     * @param params 参数
     * @return 所代表远程资源的响应结果
     */
    public static JSONObject doGet(String url, Map<String, Object> params) {
        try {
            HttpResponse response = HttpRequest.get(url)
                    .form(params)
                    .timeout(60000)
                    .execute();
            if (response.getStatus() == HttpStatus.SC_OK) {
                String content = response.body();
                System.out.println("Get's Content: " + content);
                return JSONUtil.parseObj(content);
            } else {
                System.out.println("Fail to Get.");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String errTime = dateFormat.format(new Date());
                System.out.println(errTime + "  response error(" + response.getStatus() + "):" + url);
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("got an error from HTTP for url: " + url, e);
        }
    }

    public static JSONObject doGet(String url) {
        return doGet(url, null);
    }

    /**
     * @param url 发送请求的URL
     * @param params 参数
     * @return 所代表远程资源的响应结果
     */
    public static JSONObject doPost(String url, Map<String, Object> params) {
        try {
            // 头信息 .header 放进去
            HttpResponse response = HttpRequest.post(url)
                    .form(params)
                    .timeout(60000)
                    .execute();
            if (response.getStatus() == HttpStatus.SC_OK) {
                String content = response.body();
                System.out.println("Post's Content: " + content);
                return JSONUtil.parseObj(content);
            } else {
                System.out.println("Fail to Post.");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String errTime = dateFormat.format(new Date());
                System.out.println(errTime + "  response error(" + response.getStatus() + "):" + url);
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("got an error from HTTP for url: " + url, e);
        }
    }

    public static JSONObject doPost(String url) {
        return doPost(url, null);
    }

//    /**
//     * 获取响应HTTP实体内容
//     * @param response 响应内容
//     * @return 内容
//     */
//    private static String getHttpEntityContent(HttpResponse response) throws IOException, UnsupportedEncodingException {
//        HttpEntity entity = response.getEntity();
//        if (entity != null) {
//            InputStream is = entity.getContent();
//            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
//            String line = br.readLine();
//            StringBuilder sb = new StringBuilder();
//            while (line != null) {
//                sb.append(line).append("\n");
//                line = br.readLine();
//            }
//            return sb.toString();
//        }
//        return "Empty Response";
//    }

}
