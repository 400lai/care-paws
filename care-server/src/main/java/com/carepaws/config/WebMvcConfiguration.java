package com.carepaws.config;

import com.carepaws.json.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration implements WebMvcConfigurer {



    /**
     * 扩展Spring MVC框架的消息转化器
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器...");
        // 创建一个消息转化器对象
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        // 设置自定义对象映射器
        converter.setObjectMapper(new JacksonObjectMapper());
        // 将自定义消息转换器添加到容器中（优先级低于默认转换器）
        // 这样SpringDoc可以使用默认的JSON序列化方式，而我们的API仍然可以使用自定义配置
        converters.add(converter);
    }

}