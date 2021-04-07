package com.bnuz.aed.common.tools.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.apache.http.HttpStatus;
import cn.hutool.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author Leia Liang
 * @date 2020/9/16
 * 处理Get和Post请求
 * 返回值为响应的json字符串
 */
public class HttpsClientUtils {

    static Logger logger = LoggerFactory.getLogger(HttpsClientUtils.class);

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
                logger.info("Get's Content: " + content);
                return JSONUtil.parseObj(content);
            } else {
                logger.error("Fail to Get.");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String errTime = dateFormat.format(new Date());
                logger.error(errTime + "  response error(" + response.getStatus() + "):" + url);
                return null;
            }
        } catch (Exception e) {
            logger.error("got an error from HTTP for url: " + url);
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
                logger.info("Post's Content: " + content);
                return JSONUtil.parseObj(content);
            } else {
                logger.error("Fail to Post.");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String errTime = dateFormat.format(new Date());
                logger.error(errTime + "  response error(" + response.getStatus() + "):" + url);
                return null;
            }
        } catch (Exception e) {
            logger.error("got an error from HTTP for url: " + url);
            throw new RuntimeException("got an error from HTTP for url: " + url, e);
        }
    }

    public static JSONObject doPost(String url) {
        return doPost(url, null);
    }

}
