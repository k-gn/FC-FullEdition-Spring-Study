package com.example.springbootstudy.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

//@ConfigurationProperties("my") // 이 방법을 권장
@ConfigurationProperties("my")
@ConstructorBinding // 생성자 주입
//@Configuration // ConfigurationPropertiesScan 존재 시 생략 가능
public class MyProperties {

    private final Integer height;

    public MyProperties(Integer height) {
        this.height = height;
    }

    public Integer getHeight() {
        return height;
    }

    // 기본적으로 set 으로 주입
//    public void setHeight(Integer height) {
//        this.height = height;
//    }
}
