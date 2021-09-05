package com.fastcampus.springboot;

import com.fastcampus.springboot.logic.BubbleSort;
import com.fastcampus.springboot.properties.MyProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@ConfigurationPropertiesScan
@SpringBootApplication(
//        exclude = WebMvcAutoConfiguration.class // 자동 설정 제외
//        excludeName = "org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration" // 이름 사용 (외부 라이브러리 제외 시 사용)
//        scanBasePackages = "com.fastcampus.springboot" // componentScan
)
public class SpringbootJavaOopApplication {

    // 설정파일 읽는 방법
//    @Value("${my.height}") // 인스턴스화 이후에 주입된다, final 불가능 (생성자를 사용하면 다 해결 가능)
    private final Integer height;
    private final Environment environment;
    private final ApplicationContext applicationContext;
    private final MyProperties myProperties;

    public SpringbootJavaOopApplication(
            @Value("${my.height}") Integer height,
            Environment environment,
            ApplicationContext applicationContext,
            MyProperties myProperties) {
        this.height = height;
        this.environment = environment;
        this.applicationContext = applicationContext;
        this.myProperties = myProperties;
    }

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication((SpringbootJavaOopApplication.class));
//        SpringApplication.run(SpringbootJavaOopApplication.class, args);
//        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);

//        SpringbootJavaOopApplication mainapp = new SpringbootJavaOopApplication();
//        mainapp.abc();

    }

    private void abc() {
        System.out.println("abc : " + height);
    }

    @PostConstruct // 의존성 주입이 이루어진 후 초기화를 수행
    public void init() {
        System.out.println("init : " + height);
        System.out.println("enviroment : " + environment.getProperty("my.height"));
        System.out.println("context : " + applicationContext.getEnvironment().getProperty("my.height"));
        System.out.println("myproperties : " + myProperties.getHeight());
    }

}
