package com.example.getinline.controller;

import com.example.getinline.exception.GeneralException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// view controller
@Controller
public class BaseController { //implements ErrorController {

    // 기본적으로 starting page (static or templates) 에 index.html 이 있으면 welcome page 로 자동 인식한다.
    // 그래서 아래 매핑을 안해도 되지만 추가적인 동작 설정이 필요하면 작성해야함
    // (WebMvcAutoConfiguration.java -> WelcomePageHandlerMapping)
    @GetMapping("/")
    public String root() throws Exception {
        // 콘솔에 드는 에러 stacktrace 는 볼륨이 큰 편이라
        // 사용량이 많은 서비스일 경우 해당 시스템의 로그 스페이스를 잡아먹는다.
        // 추가적으로 보안 문제도 있다.
        // 따라서 가능하면 케어해주는게 좋다.
//        throw new Exception("테스트");
//        throw new GeneralException("테스트");
        return "index";
    }

    // custom error control
    // ErrorController 을 구현해야 한다. (Marker interface)
    // 모든 request 에 대응하기 위해 RequestMapping 사용
//    @RequestMapping("/error")
//    public String error() {
//        return "error";
//    }


    // # @RequestMapping 속성
    // - 어떤 request mapping 인지 정보를 제공하거나, 어떤 요청만 들어올 수 있는지 필터를 제공하는 식으로 구성
    // - name : 뷰 템플릿에서 식별할 때 쓰는 이름 (클래스 대문자만 따온 문자열 + # + 메소드명으로 기본 name이 구성되서 직접 쓸 필욘 거의 없음)
    // - value, path : URI 경로
    // - method : HTTP Method
    // - params : 파라미터 검사
    // - headers : 헤더 검사
    // - consumes : 헤더의 Content-Type 검사 (서버가 받을 수 있는지)
    // - produces : 헤더의 Accept 검사 (클라이언트가 받을 수 있는지)
    // - Chapter 02. API 설계 - 03. 요청, 응답의 설계 - Handler Methods PDF 참고

}

// 스프링 부트의 에러 기본 응답이 마음에 든다면 BasicErrorController 를 상속받아
// 오버라이드 하거나, 핸들러 메소드를 추가하는 식으로 응용하여 사용 가능
// BasicErrorController 의 핸들러 메소드
//  - errorHtml() -> 뷰 응답
//  - error() -> json body 응답

// error.html 도 index.html 처럼 welcome page 로 자동 인식된다.
// Accept 헤더 내용에 따라 보여주는 모습이 다르다. (HTML OR JSON)
// # 단일 기본 에러 페이지
//  - /resources/static/error.html
//  - /resources/public/error.html
//  - /resources/template/error.[템플릿확장자]
// # http status 별 기본 에러 페이지
//  - /resources/[static|public|template]/error/{http status 번호}.[html|템플릿확장자]
//  - /resources/[static|public|template]/error/4xx.[html|템플릿확장자]
//  - /resources/[static|public|template]/error/5xx.[html|템플릿확장자]

// # @ExceptionHandler
// 더 자세하게 에러 커스터마이징 가능
// 비즈니스 로직이 던진 예외에 반응하는 핸들러 메소드
// 특정 컨트롤러 내부 or @ControllerAdvice 컴포넌트 내부에 작성
// 특정 예외에 반응
// 컨트롤러 안에 작성 : 해당 컨트롤러만
// @ControllerAdvice 에 작성 : 프로젝트 전체
// 예외를 입력 인자로 받을 수 있다.

// # ResponseEntityExceptionHandler
// Spring MVC 에서 내부적으로 발생하는 예외들을 처리하는 클래스
// API 예외 처리를 담당하는 @ControllerAdvice 클래스에서 상속 받아 사용
// body 부분을 null 로 내려주기 때문에 body 를 넣어주려면 수정해야함
// 커스터마이징을 원하는 특정 메소드를 오버라이드 하여 사용
