package com.example.springbootstudy;

import com.example.springbootstudy.properties.MyProperties;
import com.example.springbootstudy.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.event.EventListener;

@EnableConfigServer
//@RequiredArgsConstructor
@ConfigurationPropertiesScan
@SpringBootApplication(
//        exclude = WebMvcAutoConfiguration.class // 자동 설정 제외
//        excludeName = "org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration" // 이름 사용 (외부 라이브러리 제외 시 사용)
//        scanBasePackages = "com.fastcampus.springboot" // componentScan
)
public class SpringbootStudyApplication {

    // 설정파일 읽는 방법
//    @Value("${my.height}") // 인스턴스화 이후에 주입된다, final 불가능 (생성자를 사용하면 다 해결 가능)
//    private final Integer height;
//    private final Environment environment;
//    private final ApplicationContext applicationContext;
    private final MyProperties myProperties;
    private final StudentService studentService;

    private final String username;
    private final String password;

    public SpringbootStudyApplication(
            MyProperties myProperties,
            StudentService studentService
//            @Value("${spring.datasource.username}") String username,
//            @Value("${spring.datasource.password}") String password) {
                                                             ){
        this.myProperties = myProperties;
        this.studentService = studentService;
        this.username = "name";
        this.password = "pw";
    }

    //    public SpringbootStudyApplication(
//            @Value("${my.height}") Integer height,
//            Environment environment,
//            ApplicationContext applicationContext,
//            MyProperties myProperties) {
//        this.height = height;
//        this.environment = environment;
//        this.applicationContext = applicationContext;
//        this.myProperties = myProperties;
//    }

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication((SpringbootStudyApplication.class));
//        SpringApplication.run(SpringbootJavaOopApplication.class, args);
//        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);

//        SpringbootJavaOopApplication mainapp = new SpringbootJavaOopApplication();
//        mainapp.abc();

    }

//    private void abc() {
//        System.out.println("abc : " + height);
//    }

//    @PostConstruct // 해당 클래스의 의존성 주입이 이루어진 후 초기화를 수행
    @EventListener(ApplicationReadyEvent.class) // 모든 빈을 다 읽고 준비가 끝나면 동작, (ApplicationRunner 로 해도 된다.)
    public void init() {
//        System.out.println("init : " + height);
//        System.out.println("enviroment : " + environment.getProperty("my.height"));
//        System.out.println("context : " + applicationContext.getEnvironment().getProperty("my.height"));
//        System.out.println("myproperties : " + myProperties.getHeight());

//        studentService.printStudent("jack");
//        studentService.printStudent("jack");
//        studentService.printStudent("jack");

        System.out.println("id : " + username);
        System.out.println("pw : " + password);

    }

}

// Vault
// - 민감정보 관리에 사용하는 오픈소스 도구
// - 민감정보의 저장, 관리
// - 민감정보에 접근하는 인증/권한 관리
// - 데이터 암호화
// - 볼트 서버를 띄워놓고 그 안에 민감정보를 몰아서 관리
// 장점
// - 비즈니스 서비스와 민감정보 관리 시스템의 완전한 분리
// - 보안성 강화
// - 민감 정보에 접근하고 고객과 공유할 수 있는 다양한 방법을 사용할 수 있다.
// 단점
// - 설계에 따라 볼트서버가 죽으면 인증이 안되서 서비스가 중단되는 문제가 발생할 수 있다. (SPoF)
// - 초기 러닝커브
// - 볼트 서버를 별도로 운영해야한다.

// # 스프링 부트의 볼트 사용 지원
// - Spring Vault : Vault 연동을 위한 기본 기능 지원 (spring-vault-core)
//      : 자바 클래스로 설정해야함
// - Spring Cloud Vault : Vault가 외부 환경(클라우드)에 있는 경우를 위한 추가적인 지원
//      : 각종 설정을 프로퍼티 기반으로 조작 가능 (spring-cloud-starter-vault-config)
// - Spring Cloud Vault 를 사용하면 된다.
// - 스프링 애플리케이션이 뜰 때 최초 한번 실행되며, 볼트 서버와 인증절차를 주고받은 후 필요한 key 값을 한번에 전부 가져온다.

// # 도입 검토
// - 프로젝트가 오픈소스
// - 금융이나 상거래 관련 서비스를 하면서 민감 정보를 다룰 때
// - 기타 서비스 도메인이 법에 민감한 분야라고 판단될 때
// - 제품 코드(서비스)와 민감 정보를 분리하고자 할 때
