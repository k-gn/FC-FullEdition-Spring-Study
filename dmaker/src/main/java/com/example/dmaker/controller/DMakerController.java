package com.example.dmaker.controller;

import com.example.dmaker.dto.CreateDeveloper;
import com.example.dmaker.dto.DeveloperDetailDto;
import com.example.dmaker.dto.DeveloperDto;
import com.example.dmaker.dto.EditDeveloper;
import com.example.dmaker.service.DeveloperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    // entity 를 리턴하는건 좋지 않은 패턴
    // 불필요한 정보가 나갈 수 있고, 트랜잭션이 없는 상태에서 접근 시 문제가 발생할 수 있다.
    // entity 와 응답 데이터를 분리해주는 것이 좋다.
    public List<DeveloperDto> getAllDevelopers() {

        log.info("GET /developers HTTP/1.1");

        return developerService.getAllEmployedDevelopers();
    }

    @GetMapping("/developer/{memberId}")
    public DeveloperDetailDto getAllDeveloperDetail(@PathVariable String memberId) {

        log.info("GET /developer HTTP/1.1");

        return developerService.getDeveloperDetail(memberId);
    }

    @PostMapping("/create-developers")
    public CreateDeveloper.Response createDevelopers(@Valid @RequestBody CreateDeveloper.Request request) {

        log.info("POST /create-developers HTTP/1.1");
        log.info("request : {}", request);

        return developerService.createDeveloper(request);
    }

    @PutMapping("/developer/{memberId}")
    public DeveloperDetailDto editDeveloper(@PathVariable String memberId, @Valid @RequestBody EditDeveloper.Request request) {

        log.info("GET /developer HTTP/1.1");

        return developerService.editDeveloper(memberId, request);
    }

    @DeleteMapping("/developer/{memberId}")
    public DeveloperDetailDto deleteDeveloper(@PathVariable String memberId) {
        return developerService.deleteDeveloper(memberId);
    }

}

/*
   # H2 Database
   - 관계형 데이터베이스
   - 메모리 DB를 주로 사용
   - 따로 설치없이 사용 가능해서 테스트용으로 편리
*/