package com.bookiply.interview.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.retry.annotation.EnableRetry;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableConfigurationProperties
@EnableRetry
@EnableSwagger2
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
