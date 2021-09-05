package com.example.springbootstudy;

import com.example.springbootstudy.domain.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class SpringbootStudyApplicationTests {

    @Autowired
    private ObjectMapper mapper;

    private static Logger logger = LoggerFactory.getLogger(SpringbootStudyApplicationTests.class);

    @Container // private static 로 해야함, 외부 장치를 도커 컨테이너로 만들어 등록 -> 다른 지원이 없기 떄문에 GenericContainer<?> 를 사용해야 한다.
    private static final GenericContainer<?> redisContainer = new GenericContainer<>(DockerImageName.parse("redis:latest"));

    @BeforeAll
    static void setup() {
        redisContainer.followOutput(new Slf4jLogConsumer(logger));
    }

    @DynamicPropertySource // 테스트용, 프로퍼티를 나중에 읽어서 주입
    static void properties(DynamicPropertyRegistry registry) {
//        registry.add("spring.cache.type", () -> "redis");
        // 테스트 시 Redis 포트가 랜덤으로 설정되서 해당 포트번호를 가져와서 매핑
        registry.add("spring.redis.port", () -> redisContainer.getMappedPort(6379));
    }

                           @Test
    void contextLoads() throws IOException, InterruptedException {
        // given

        // when
        GenericContainer.ExecResult execResult1 = redisContainer.execInContainer("redis-cli", "get", "student:cassie");
        GenericContainer.ExecResult execResult2 = redisContainer.execInContainer("redis-cli", "get", "student:fred");
        Student actual1 = mapper.readValue(execResult1.getStdout(), Student.class);
        Student actual2 = mapper.readValue(execResult2.getStdout(), Student.class);

        // then
        assertThat(redisContainer.isRunning()).isTrue();
        assertThat(actual1).isEqualTo(Student.of("cassie", 18, Student.Grade.A));
        assertThat(actual1).isEqualTo(Student.of("jack", 30, Student.Grade.A));

    }

}
// # TestContainers
// - DB등의 외부장치를 코드로 표현하고, 자동으로 도커 이미지로 만들고 등록/해제
// - 외부 장치와 연관이 있는 부분의 통합테스트를 용이하게 한다.
// - JUnit 호환
// - 로컬이 아닌 컨테이너 (도커)에 띄운 후 테스트

// redis 를 띄워보자!
// - redis를 컨테이너로 만들고 소스코드로 표현이 가능해졌다. (단, 도커 설치해야함)
// - 테스트용 컨테이너가 자동으로 올라가고 내려가서 편리. (단, 그만큼 그 부분의 속도는 느려진다.)
// - 컨테이너로 등록하여 동적으로 변하는 설정 = @DynamicPropertySource
// - Logger 주입으로 컨테이너 내부 동작 관찰 가능
// - 잘 안되는 경우도 있다 (Vault)
// - 문서와 자료가 좀 부족하다.


// # Spring Native
// # Native?
// - GraalVM : Hotspot JVM(c++)의 개발 한계를 극복하기 위한 Meta-circular(해당 언어의 컴파일러를 해당 언어로 만든 것) JVM
//                                                      (java 로 만든 JVM / 성능, 클라우드 환경, 다양성 고려)
// - AOT (ahead-of-time compile) : 미리 기계어로 번역 -> 번역이 이미 끝났으니 속도가 더 빠르고, 런타임에 컴파일러가 필요없어서 더 가볍다.
//        : JIT - 중간언어(Byte code) -> 기계어 (runtime)
//        : AOT - 중간언어(Byte code) -> 기계어 (compile time, build 시)
//        : Static Compiler - 소스코드 -> 기계어 (compile time)
//        : AOT compiler 를 이용해 native image 빌드, 정적 분석과정 포함, 네이티브 바이너리 결과물은 즉시 실행 가능한 기계코드 전체를 포함(JVM 불필요)
//          다른 네이티브 이미지와 링크 가능, 클라우드 네이티브 애플리케이션 배포에 효과적일 것으로 기대
// - 고성능
// - 적은 메모리 사용
// - 경략 도커 컨테이너를 만들어 배포 (기본 기능) , 로컬에서도 가능
// - sdkman, GraalVM, docker 설치 필요 (명령어는 Spring Native 강의 자료 참고)
// - 추가되는 gradle 설정과 코드는 스프링 부트:Spring Native 강의 13분 부터 확인


// # Spring Cloud Config
// - 외부 환경 설정을 관리하는 독립적인 서버
// - 설정을 바꾸고 싶으면 파일 내용만 바꾸면 끝
// - git repository 지원
// - 분산환경에서 다양한 스프링 부트 애플리케이션이 접근하기 용이
