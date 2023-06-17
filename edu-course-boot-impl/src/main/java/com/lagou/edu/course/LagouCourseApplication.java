package com.lagou.edu.course;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * LagouCourseApplication
 *
 * @author xianhongle
 * @data 2022/6/3 16:51
 **/
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.lagou.edu.course.mapper")
@EnableFeignClients("com.lagou.edu")
public class LagouCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(LagouCourseApplication.class,args);
    }

}
