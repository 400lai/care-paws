package com.carepaws.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "care.alioss")
@Data
public class AliOssProperties {

    // 存储服务端点地址
    private String endpoint;

    // 访问密钥ID，用于身份验证
    private String accessKeyId;

    // 访问密钥Secret，用于身份验证
    private String accessKeySecret;

    // 存储桶名称，指定数据存储的目标容器
    private String bucketName;

}
