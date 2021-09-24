package com.example.getinline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan // ConfigurationProperties 을 찾을 수 있어진다.
public class GetInLineApplication {

    public static void main(String[] args) {
        SpringApplication.run(GetInLineApplication.class, args);
    }

}