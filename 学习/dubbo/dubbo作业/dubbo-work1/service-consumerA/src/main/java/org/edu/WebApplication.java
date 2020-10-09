package org.edu;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableDubbo(scanBasePackages="com.edu")
public class WebApplication{

    public static void main(String[] args)  {
        SpringApplication.run(WebApplication.class, args);
    }


}
