package com.example.dmaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing // auditing 기능 사용하기
@SpringBootApplication
public class DmakerApplication {

    public static void main(String[] args) {

        SpringApplication.run(DmakerApplication.class, args);

    }


}
