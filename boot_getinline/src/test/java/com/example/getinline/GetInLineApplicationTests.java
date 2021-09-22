package com.example.getinline;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// @SpringBootTest
// 스프링 컨테이너와 스프링 부트 환경을 포함하는 테스트 모두 이것으로 다 작성 가능
// 통합 테스트 시 적절하다.
// 애플리케이션 컨텍스트를 로드하는데 시간이 걸리므로 다소 무겁다.
// 이상적으로는 필요한 설정만 불러오는 방법이 좋다. (slice test)
// @BootstrapWith + @ExtendWith
// JUnit5 -> @ExtendWith(SpringExtension.class) 쓰지 않기 (이미 포함됨)

// value, properties : 프로퍼티(속성들)  설정
// args : 애플리케이션 실행 시 커맨드라인으로 입력하는 인자 설정
// classes : ApplicationContext 로딩을 위한 설정 클래스를 직접 지정
//           기본적으로 쓰지 않으면 @SpringBootApplication 가 붙은 클래스를 로드
// webEnvironment : ApplicationContext 의 웹 환경 설정
//                  WebEnvironment.MOCK : mock servlet, embedded server 동작 X (default)
//                    - @AutoConfigureMockMvc, @AutoConfigureWebTestClient 와 함께 써서 mock test 가능
//                  WebEnvironment.RANDOM_PORT : 랜덤 포트, embedded server 동작
//                  WebEnvironment.DEFINED_PORT : 포트 지정(server.port), embedded server 동작
//                  WebEnvironment.NONE : 웹 환경 구성 안함, embedded server 동작 X

// @WebMvcTest 
// Spring mvc 컨트롤러 레이어를 슬라이스 테스트 할 때 사용
// MockMvc 빈을 자동 설정하고 테스트에 사용한다.
// 로드할 컨트롤러 클래스를 지정 가능 (기본은 전체 컨트롤러 로드)
@SpringBootTest
@Disabled("다른 테스트의 속도 향상을 위해 비활성화")
@DisplayName("스프링 부트 기본 테스트")
class GetInLineApplicationTests {

    // 단위 테스트 메소드 지정
    @Test
    void contextLoads() {
    }

}
