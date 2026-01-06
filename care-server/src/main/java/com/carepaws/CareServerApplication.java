package com.carepaws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ServletComponentScan
@SpringBootApplication
@EnableTransactionManagement //开启注解方式的事务管理
@Slf4j
@EnableCaching  // 开启缓存注解功能
public class CareServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CareServerApplication.class, args);
        log.info("server started:服务启动成功！");
    }
}
