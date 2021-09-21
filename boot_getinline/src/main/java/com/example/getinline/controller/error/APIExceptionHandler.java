package com.example.getinline.controller.error;

import com.example.getinline.constant.ErrorCode;
import com.example.getinline.dto.APIErrorResponse;
import com.example.getinline.exception.GeneralException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice(annotations = RestController.class) // @ControllerAdvice + @ResponseBody
public class APIExceptionHandler extends ResponseEntityExceptionHandler { // Spring MVC에서 기본으로 제공되는 Exception들의 처리를 간단하게 등록

    // 스프링 부트의 Spring Web mvc 안에선 다양한 작업들이 동작하고 있고,
    // 또 그안에서 스프링 부트가 지정해놓은 에러들이 나타난다.
    // 이러한 에러도 따로 잡아주면 좋다. (따로 처리하지 않을 경우 공통 에러 페이지나 Exception 핸들링으로 빠진다)
    // API 에 한에서 스프링 웹에서 발생시킨, 미리 알고있는 스프링 전용 예외들을 처리하고 싶을 경우 하나하나 직접 해주기엔 너무 많다.
    // 스프링에서 만들어 놓은 ResponseEntityExceptionHandler 를 상속받아 간단하게 해당 에러들을 잡아줄 수 있다.
    // body 가 필요하면 handleExceptionInternal 를 오버라이딩 할 수 있어서 해결 가능.

    @ExceptionHandler
    public ResponseEntity<Object> general(GeneralException e, WebRequest request) {
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = errorCode.isClientSideError() ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
//        return ResponseEntity
//                .status(status)
//                .body(APIErrorResponse.of(false, errorCode, errorCode.getMessage(e)));

        // 이미 구현된 메소드를 사용 (형식을 통일)
        return super.handleExceptionInternal(e,
                APIErrorResponse.of(false, errorCode.getCode(), errorCode.getMessage(e)),
                HttpHeaders.EMPTY, status, request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception e, WebRequest request) {
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
//        return ResponseEntity
//                .status(status)
//                .body(APIErrorResponse.of(false, errorCode, errorCode.getMessage(e)));

        return super.handleExceptionInternal(e,
                APIErrorResponse.of(false, errorCode.getCode(), errorCode.getMessage(e)),
                HttpHeaders.EMPTY, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        // 동작은 똑같은데 요지는 body 만 넣어주면 되는 것!
        ErrorCode errorCode = status.is4xxClientError() ? ErrorCode.SPRING_BAD_REQUEST : ErrorCode.SPRING_INTERNAL_ERROR;
        return super.handleExceptionInternal(ex,
                APIErrorResponse.of(false, errorCode.getCode(), errorCode.getMessage(ex)),
                headers, status, request);
    }
}
