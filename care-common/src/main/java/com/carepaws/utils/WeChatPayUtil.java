package com.carepaws.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.carepaws.properties.WeChatProperties;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

/**
 * 微信支付工具类（必须使用 wechatpay-apache-httpclient）
 */
@Component
public class WeChatPayUtil {

    public static final String JSAPI = "https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi";
    public static final String REFUNDS = "https://api.mch.weixin.qq.com/v3/refund/domestic/refunds";

    @Autowired
    private WeChatProperties weChatProperties;

    private volatile PrivateKey merchantPrivateKey;
    private volatile List<X509Certificate> wechatPayCertificates;

    private CloseableHttpClient getClient() {
        if (merchantPrivateKey == null || wechatPayCertificates == null) {
            synchronized (this) {
                if (merchantPrivateKey == null || wechatPayCertificates == null) {
                    try {
                        merchantPrivateKey = PemUtil.loadPrivateKey(new FileInputStream(new File(weChatProperties.getPrivateKeyFilePath())));
                        X509Certificate cert = PemUtil.loadCertificate(new FileInputStream(new File(weChatProperties.getWeChatPayCertFilePath())));
                        wechatPayCertificates = Arrays.asList(cert);

                    } catch (Exception e) {
                        throw new RuntimeException("Failed to load WeChat pay certificates or private key", e);
                    }
                }
            }
        }

        return WechatPayHttpClientBuilder.create()
                .withMerchant(weChatProperties.getMchid(), weChatProperties.getMchSerialNo(), merchantPrivateKey)
                .withWechatPay(wechatPayCertificates)
                .build();
    }

    private String post(String url, String body) throws Exception {
        try (CloseableHttpClient httpClient = getClient()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
            httpPost.setEntity(new StringEntity(body, StandardCharsets.UTF_8));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            }
        }
    }

    private String get(String url) throws Exception {
        try (CloseableHttpClient httpClient = getClient()) {
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
            httpGet.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            }
        }
    }

    public JSONObject pay(String orderNum, BigDecimal total, String description, String openid) throws Exception {
        // 1. 统一下单
        JSONObject req = new JSONObject();
        req.put("appid", weChatProperties.getAppid());
        req.put("mchid", weChatProperties.getMchid());
        req.put("description", description);
        req.put("out_trade_no", orderNum);
        req.put("notify_url", weChatProperties.getNotifyUrl());

        JSONObject amount = new JSONObject();
        int totalFen = total.multiply(BigDecimal.valueOf(100)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        amount.put("total", totalFen);
        amount.put("currency", "CNY");
        req.put("amount", amount);

        JSONObject payer = new JSONObject();
        payer.put("openid", openid);
        req.put("payer", payer);

        String respBody = post(JSAPI, req.toJSONString());
        JSONObject resp = JSON.parseObject(respBody);
        System.out.println("WeChat Pay Response: " + resp);

        String prepayId = resp.getString("prepay_id");
        if (prepayId == null) {
            return resp; // 返回错误信息
        }

        // 2. 生成小程序支付参数
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonceStr = RandomStringUtils.randomAlphanumeric(32);

        // 拼接签名串（注意最后也有 \n）
        String message = String.join("\n",
                weChatProperties.getAppid(),
                timeStamp,
                nonceStr,
                "prepay_id=" + prepayId,
                ""
        );

        // 使用缓存的私钥签名
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(merchantPrivateKey);
        signature.update(message.getBytes(StandardCharsets.UTF_8));
        String paySign = Base64.getEncoder().encodeToString(signature.sign());

        JSONObject result = new JSONObject();
        result.put("timeStamp", timeStamp);
        result.put("nonceStr", nonceStr);
        result.put("package", "prepay_id=" + prepayId);
        result.put("signType", "RSA");
        result.put("paySign", paySign);

        return result;
    }

    public String refund(String outTradeNo, String outRefundNo, BigDecimal refund, BigDecimal total) throws Exception {
        JSONObject req = new JSONObject();
        req.put("out_trade_no", outTradeNo);
        req.put("out_refund_no", outRefundNo);

        JSONObject amount = new JSONObject();
        int refundFen = refund.multiply(BigDecimal.valueOf(100)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        int totalFen = total.multiply(BigDecimal.valueOf(100)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        amount.put("refund", refundFen);
        amount.put("total", totalFen);
        amount.put("currency", "CNY");
        req.put("amount", amount);
        req.put("notify_url", weChatProperties.getRefundNotifyUrl());

        return post(REFUNDS, req.toJSONString());
    }
}