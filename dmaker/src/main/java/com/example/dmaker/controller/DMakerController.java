package com.example.dmaker.controller;

import com.example.dmaker.service.DeveloperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// 빈 등록 (컴포넌트 - 최소한의 단위)
// 컴포넌트의 특수한 타입 Controller
// ResponseBody : json 으로 리턴 (순수한 api = json 요청과 json 응답)
// 요청쪽에 Accept 하는 Content type 에 따라서 응답을 바꿔줘야 하지만 기본적으로는 json 이 거의 표준
@RestController // 사용자 요청을 받아 json 으로 응답을 내려주는 컨트롤러
@Slf4j
@RequiredArgsConstructor
public class DMakerController {

    private final DeveloperService developerService;

    // GET /developers HTTP/1.1
    @GetMapping("/developers")
    public List<String> getAllDevelopers() {

        log.info("GET /developers HTTP/1.1");

        return Arrays.asList("snow", "elsa", "olaf");
    }

    @GetMapping("/create-developers")
    public List<String> createDevelopers() {

        log.info("GET /create-developers HTTP/1.1");

        developerService.createDeveloper();

        return Collections.singletonList("Olaf");
    }
}

/*
   # H2 Database
   - 관계형 데이터베이스
   - 메모리 DB를 주로 사용
   - 따로 설치없이 사용 가능해서 테스트용으로 편리
*/