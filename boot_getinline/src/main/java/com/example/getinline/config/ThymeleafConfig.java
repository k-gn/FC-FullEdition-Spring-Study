package com.example.getinline.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import javax.swing.*;

@Configuration
public class ThymeleafConfig {

    // 타임리프 decoupled 활성화 설정
    // 기존의 SpringResourceTemplateResolver 를 사용하면서 decoupled 만 활성화 추가
    @Bean
    public SpringResourceTemplateResolver templateResolver(
            SpringResourceTemplateResolver defaultTemplateResolver,
            Thymeleaf3Properties thymeleaf3Properties
    ) {
        defaultTemplateResolver.setUseDecoupledLogic(thymeleaf3Properties.isDecoupledLogic());
        return defaultTemplateResolver;
    }

    @Getter
    @RequiredArgsConstructor
    @ConstructorBinding // 생성자로 바인딩 - final 사용 가능해짐!
    @ConfigurationProperties("spring.thymeleaf3")
    public static class Thymeleaf3Properties {
        /**
         * Thymeleaf 3 Decoupled Logic 기능 활성화
         */
        private final boolean decoupledLogic;
    }
}
