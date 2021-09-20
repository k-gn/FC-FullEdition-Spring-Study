package com.example.getinline.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.List;

import static org.springframework.web.servlet.function.RequestPredicates.path;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration
public class APIPlaceRouter {

    @Bean
    public RouterFunction<ServerResponse> placeRouter(APIPlaceHandler apiPlaceHandler) {
        return route().nest(path("/api/places"), builder -> builder
                .GET("", apiPlaceHandler::getPlaces)
                .POST("", apiPlaceHandler::createPlace)
                .GET("/{placeId}", apiPlaceHandler::getPlace)
                .PUT("/{placeId}", apiPlaceHandler::modifyPlace)
                .DELETE("/{placeId}", apiPlaceHandler::removePlace)
        ).build();

    }
}
// # 함수형 프로그래밍이란
//  - 자료처리를 수학적 함수의 계산으로 취급하고, 상태와 가변 데이터를 멀리하는 프로그래밍 패러다임의 하나
//  - 대입문이 없는 프로그래밍
// 1. 상태가 없고 (멤버 변수가 없다), 대입문이 없다.
// 2. 부작용(side effect)이 없는 순수 함수
// 3. 불변성 (immutability)
// 현대 소프트 아키텍처가 점점 발전하고 복잡해 지면서 주목 받는 것 같다.
// 요즘은 큰 엔터프라이즈급 서비스 개발 시 여러 서비스(모듈)들을 분산형으로 설계하는 경우가 많다.
// 다양한 서비스들이 서로 분산된 상태에서 상호 통신을 한다.
// 또는 멀티코어 환경에서 좋은 퍼포먼스를 보이기 위해 병렬처리를 하는데
// 이러한 환경들에서 나오는 복잡한 요구사항들이 함수형 스타일과 잘 맞는다. (코드를 이해하고, 테스트하고, 유지보수하는데 용이)

// # 함수형 엔드포인트
// Spring Web 의 엔드포인트를 함수형 스타일로 작성하는 방법을 제공
// WebMvc.fn
// routing, request handling
// 불변성을 고려하여 설계됨
// 기존의 DispatcherServlet 위에서 동작
// 애노테이션 스타일과 함께 사용 가능 (즉, 부분적 도입 가능)

// # 주요 키워드
// HandlerFunction == @RequestMapping -> 결과가 data
//  입력 : ServerRequest
//  출력 : ServerResponse
// RouterFunction == @RequestMapping -> 결과가 data + behavior (ex. url mapping)
//  입력 : ServerRequest
//  출력 : Optional<HandlerFunction>