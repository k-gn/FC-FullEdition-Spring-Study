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


}
