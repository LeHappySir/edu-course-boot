package com.lagou.edu.course.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * AliyunConfig
 *
 * @author xianhongle
 * @data 2022/6/3 19:26
 **/
@Data
@Configuration
@PropertySource("classpath:aliyun.properties")
@ConfigurationProperties(prefix = "aliyun")
public class AliyunConfig {

    private String accessKeyId;

    private String accessKeySecret;

}
