package com.example.dmaker.exception;

import com.example.dmaker.dto.DMakerErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static com.example.dmaker.exception.DMakerErrorCode.INTERNAL_SERVER_ERROR;
import static com.example.dmaker.exception.DMakerErrorCode.INVALID_REQUEST;

// 어플리케이션 단위의 예외 처리
// @Controller나 @RestController에서 발생한 예외를 한 곳에서 관리하고 처리할 수 있게 도와주는 어노테이션
// @ExceptionHandler, @ModelAttribute, @InitBinder 가 적용된 메서드들을 AOP를 적용해 컨트롤러 단에 적용하기 위해 고안된 애너테이션
// @ResponseBody + @ControllerAdvice => @RestControllerAdvice
// 예외 처리가 없다면 ?
//  - 상대방 화면 또는 서버가 어떤 에러인지 정확히 알기 어려운 문제가 발생할 수 있다.
//  - 우리가 에러메시지를 마음대로 컨트롤 할 수 없다.
//  - 변경에 의한 장애가 발생할 수 있다.
//  - 우리가 정의한 대로 에러메시지를 보내는 것이 처리하는데 더 좋다.
//  - 불필요할 정도로 디테일한 에러가 담겨 보안에 취약해질 수 있다. (정제된 에러가 필요)
@RestControllerAdvice
@Slf4j
public class DMakerExceptionHandler {

    @ExceptionHandler(DMakerException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public DMakerErrorResponse handleException(DMakerException e, HttpServletRequest request) {

        log.error("errorCode : {}, url : {}, message : {}", e.getDMakerErrorCode(), request.getRequestURL(), e.getDetailMessage());

        return DMakerErrorResponse.builder()
                .errorCode(e.getDMakerErrorCode())
                .errorMessage(e.getDetailMessage())
                .build();
    }

    @ExceptionHandler(value = {
            HttpRequestMethodNotSupportedException.class, // 요청 method 를 잘못 보낼 때
            MethodArgumentNotValidException.class // validation 문제 발생 시
    })
    public DMakerErrorResponse handleBadRequest(Exception e, HttpServletRequest request) {
        log.error("url : {}, message : {}", request.getRequestURL(), INVALID_REQUEST.getMessage());

        return DMakerErrorResponse.builder()
                .errorCode(INVALID_REQUEST) // 기본적으로 ENUM 은 리턴 시 String 으로 리턴됨
                .errorMessage(INVALID_REQUEST.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public DMakerErrorResponse handleException(Exception e, HttpServletRequest request) {
        log.error("url : {}, message : {}", request.getRequestURL(), e.getMessage());

        return DMakerErrorResponse.builder()
                .errorCode(INTERNAL_SERVER_ERROR) 
                .errorMessage(INTERNAL_SERVER_ERROR.getMessage())
                .build();
    }
}
