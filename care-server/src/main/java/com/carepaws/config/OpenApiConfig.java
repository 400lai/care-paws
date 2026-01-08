package com.carepaws.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        Contact contact = new Contact()
                .name("CarePaws Team")
                .email("support@carepaws.com")
                .url("https://www.carepaws.com");

        return new OpenAPI()
                .info(new Info()
                        .title("CarePaws API文档")
                        .description("宠物护理系统 API 接口文档")
                        .version("1.0")
                        .contact(contact)
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}
