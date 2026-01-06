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

    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    public static String doGet(String url, Map<String, String> params) throws IOException, InterruptedException {
        String query = params == null ? "" : buildQueryString(params);
        URI uri = URI.create(url + (query.isEmpty() ? "" : "?" + query));
        HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static String doPostForm(String url, Map<String, String> params) throws IOException, InterruptedException {
        String body = buildFormBody(params);
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static String doPostJson(String url, Map<String, String> params) throws IOException, InterruptedException {
        JSONObject json = new JSONObject();
        if (params != null) {
            json.putAll(params);
        }
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toJSONString(), StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

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

    private static String buildFormBody(Map<String, String> params) throws UnsupportedEncodingException {
        return buildQueryString(params);
    }
}