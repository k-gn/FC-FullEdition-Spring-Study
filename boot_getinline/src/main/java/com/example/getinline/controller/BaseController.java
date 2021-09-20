package com.example.getinline.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// view controller
@Controller
public class BaseController implements ErrorController {

    // 기본적으로 starting page (static or templates) 에 index.html 이 있으면 welcome page 로 인식한다.
    // 그래서 아래 매핑을 안해도 되지만 추가적인 동작 설정이 필요하면 작성해야함
    // (WebMvcAutoConfiguration.java -> WelcomePageHandlerMapping)
    @GetMapping("/")
    public String root() {
        return "index";
    }

    // custom error control
    // ErrorController 을 구현해야 한다. (Marker interface)
    // 모든 request 에 대응하기 위해 RequestMapping 사용
    @RequestMapping("/error")
    public String error() {
        return "error";
    }


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
