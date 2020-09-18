package org.bnuz.aedminiprogram.common.tools;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Leia Liang
 * @date 2020/9/16
 * 处理Get和Post请求
 * 返回值为响应的json字符串
 */
public class HttpsClientUtil {

    /**
     * @param url 发送请求的URL
     * @param params 参数
     * @return 所代表远程资源的响应结果
     */
    public static String doGet(String url, Map<String, String> params) {
        String result = "";
        HttpGet get = new HttpGet();
        HttpResponse response = null;
        // 设置请求和传输超时时间
        RequestConfig config = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).build();
        get.setConfig(config);
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (null != params && !params.isEmpty()) {
                for (Map.Entry<String , String> entry : params.entrySet()) {
                    uriBuilder.addParameter(entry.getKey(), entry.getValue());
                }
            }
            get.setURI(uriBuilder.build());
            HttpClient client = HttpClients.createDefault();
            response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String content = getHttpEntityContent(response);
                System.out.println("Get's Content: " + content);
                result = EntityUtils.toString(response.getEntity());
            } else {
                System.out.println("Fail to Get.");
            }
        } catch (Exception e) {
            throw new RuntimeException("got an error from HTTP for url: " + url, e);
        } finally {
            // 释放实体
            if (response != null) {
                EntityUtils.consumeQuietly(response.getEntity());
            }
            // 释放连接
            get.releaseConnection();
        }
        return result;
    }

    public static String doGet(String url) {
        String result = "";
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet();
        HttpResponse response = null;
        // 设置请求和传输超时时间
        RequestConfig config = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).build();
        get.setConfig(config);
        try {
            get.setURI(new URI(url));
            response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String content = getHttpEntityContent(response);
                System.out.println("Get's Content: " + content);
                result = EntityUtils.toString(response.getEntity());
            } else {
                System.out.println("Fail to Get.");
            }
        } catch (Exception e) {
            throw new RuntimeException("got an error from HTTP for url: " + url, e);
        } finally {
            // 释放实体
            if (response != null) {
                EntityUtils.consumeQuietly(response.getEntity());
            }
            // 释放连接
            get.releaseConnection();
        }
        return result;
    }

    /**
     * @param url 发送请求的URL
     * @param params 参数
     * @return 所代表远程资源的响应结果
     */
    public static String doPost(String url, Map<String, String> params) {
        String result = "";
        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        HttpResponse response = null;
        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).build();
        post.setConfig(requestConfig);
        try {
            if (null != params && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<>();
                NameValuePair pair = null;
                for (String key : params.keySet()) {
                    pair = new BasicNameValuePair(key, params.get(key));
                    pairs.add(pair);
                }
                UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(pairs);
                post.setEntity(encodedFormEntity);
            }
            response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String content = getHttpEntityContent(response);
                System.out.println("Post's Content: " + content);
                result = EntityUtils.toString(response.getEntity());
            } else {
                System.out.println("Fail to Post.");
            }
        } catch (Exception e) {
            throw new RuntimeException("got an error from HTTP for url: " + url, e);
        } finally {
            // 释放实体
            if (response != null) {
                EntityUtils.consumeQuietly(response.getEntity());
            }
            // 释放连接
            post.releaseConnection();
        }
        return result;
    }

    public static String doPost(String url, String data) {
        String result = "";
        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        HttpResponse response = null;
        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).build();
        post.setConfig(requestConfig);
        try {
            post.setHeader("Content-type", "application/json; charset=utf-8");
            post.setEntity(new StringEntity(URLEncoder.encode(data, "UTF-8")));
            response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String content = getHttpEntityContent(response);
                System.out.println("Post's Content: " + content);
                result = EntityUtils.toString(response.getEntity());
            } else {
                System.out.println("Fail to Post.");
            }
        } catch (Exception e) {
            throw new RuntimeException("got an error from HTTP for url: " + url, e);
        } finally {
            // 释放实体
            if (response != null) {
                EntityUtils.consumeQuietly(response.getEntity());
            }
            // 释放连接
            post.releaseConnection();
        }
        return result;
    }

    /**
     * 获取响应HTTP实体内容
     * @param response 响应内容
     * @return 内容
     */
    private static String getHttpEntityContent(HttpResponse response) throws IOException, UnsupportedEncodingException {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream is = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line = br.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line).append("\n");
                line = br.readLine();
            }
            return sb.toString();
        }
        return "Empty Response";
    }

}
