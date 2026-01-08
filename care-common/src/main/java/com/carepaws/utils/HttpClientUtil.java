package com.carepaws.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Http工具类（仅用于普通HTTP请求，不可用于微信支付！）
 */
public class HttpClientUtil {

    /**
     * HTTP客户端实例，用于发送HTTP请求
     * 配置了5秒的连接超时时间
     */
    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();


    /**
     * 执行HTTP GET请求
     */
    public static String doGet(String url, Map<String, String> params) throws IOException, InterruptedException {
        // 构建查询字符串
        String query = params == null ? "" : buildQueryString(params);
        // 构造完整的请求URI
        URI uri = URI.create(url + (query.isEmpty() ? "" : "?" + query));
        HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
        // 发送HTTP请求并获取响应
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    /**
     * 执行HTTP POST表单提交请求
     */
    public static String doPostForm(String url, Map<String, String> params) throws IOException, InterruptedException {
        // 构建表单请求体
        String body = buildFormBody(params);

        // 创建HTTP POST请求，设置表单提交的Content-Type
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                .build();

        // 发送请求并获取响应
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    /**
     * 发送POST请求，以JSON格式提交数据
     */
    public static String doPostJson(String url, Map<String, String> params) throws IOException, InterruptedException {
        JSONObject json = new JSONObject();
        if (params != null) {
            // 将参数Map中的键值对添加到JSON对象中
            json.putAll(params);
        }
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toJSONString(), StandardCharsets.UTF_8))
                .build();
        // 发送HTTP请求并获取响应
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    /**
     * 构建查询字符串，将参数Map转换为URL编码的查询字符串格式
     */
    private static String buildQueryString(Map<String, String> params) throws UnsupportedEncodingException {
        StringJoiner joiner = new StringJoiner("&");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            // 兼容 Java 8～21
            String key = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.name());
            String value = URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.name());
            joiner.add(key + "=" + value);
        }
        return joiner.toString();
    }

    /**
     * 构建表单请求体，将参数Map转换为URL编码的表单格式
     */
    private static String buildFormBody(Map<String, String> params) throws UnsupportedEncodingException {
        return buildQueryString(params);
    }
}