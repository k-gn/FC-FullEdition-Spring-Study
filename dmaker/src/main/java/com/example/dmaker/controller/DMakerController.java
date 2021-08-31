package com.example.dmaker.controller;

import com.example.dmaker.dto.*;
import com.example.dmaker.exception.DMakerException;
import com.example.dmaker.service.DeveloperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

        // 예전에는 이런 컨트롤러 안에서 비즈니스 로직을 통해 예외처리를 하였다.
        // 하지만 가능하면 컨트롤러단에선 불필요한 비즈니스 로직은 피하는게 좋다.

        log.info("GET /developers HTTP/1.1");

        return developerService.getAllEmployedDevelopers();
    }

    @GetMapping("/developer/{memberId}")
    public DeveloperDetailDto getAllDeveloperDetail(@PathVariable final String memberId) { // 들어온 요청값이 변경되면 안되므로 final

        log.info("GET /developer HTTP/1.1");

        return developerService.getDeveloperDetail(memberId);
    }

    @PostMapping("/create-developers")
    public CreateDeveloper.Response createDevelopers(@Valid @RequestBody final CreateDeveloper.Request request) {

        log.info("POST /create-developers HTTP/1.1");
        log.info("request : {}", request);

        return developerService.createDeveloper(request);
    }

    @PutMapping("/developer/{memberId}")
    public DeveloperDetailDto editDeveloper(@PathVariable final String memberId, @Valid @RequestBody final EditDeveloper.Request request) {

        log.info("GET /developer HTTP/1.1");

        return developerService.editDeveloper(memberId, request);
    }

    @DeleteMapping("/developer/{memberId}")
    public DeveloperDetailDto deleteDeveloper(@PathVariable final String memberId) {
        return developerService.deleteDeveloper(memberId);
    }


    // 과거엔 try - catch 를 한 메소드에 전부 쓰는 방식을 사용했었다..
    // (매우 불편, 외부 api와 연동할 때 호출하는 곳에서 예상치 못한 예외가 발생할 수 있어서 부분적으로 필요할 떄도 있다.)
    // 해당 컨트롤러에서 발생하는 에러를 한번에 제어 (컨트롤러 단위)
//    @ExceptionHandler(DMakerException.class) // 해당 에러가 발생하면 처리
//    @ResponseStatus(value = HttpStatus.CONFLICT) // 응답 상태코드를 바꿔 내려줄 수도 있다.
//    public DMakerErrorResponse handleException(DMakerException e, HttpServletRequest request) {
//
//        log.error("errorCode : {}, url : {}, message : {}", e.getDMakerErrorCode(), request.getRequestURL(), e.getDetailMessage());
//
//        return DMakerErrorResponse.builder()
//                .errorCode(e.getDMakerErrorCode())
//                .errorMessage(e.getDetailMessage())
//                .build();
//    }
    
}

/*
   # H2 Database
   - 관계형 데이터베이스
   - 메모리 DB를 주로 사용
   - 따로 설치없이 사용 가능해서 테스트용으로 편리
*/