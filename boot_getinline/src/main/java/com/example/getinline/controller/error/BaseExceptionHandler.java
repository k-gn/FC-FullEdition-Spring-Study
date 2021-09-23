package com.example.getinline.controller.error;

import com.example.getinline.constant.ErrorCode;
import com.example.getinline.dto.APIErrorResponse;
import com.example.getinline.exception.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

// 예외 처리 관심사 분리
// @ExceptionHandler 를 모아서 (전역)글로벌하게 적용할 때 쓰는 애노테이션
// # 속성
// value == basePackages
// basePackages : 적용 범위를 문자열을 이용해 특정 패키지로 지정 
// basePackagesClasses : 적용 범위를 대표하는 클래스 한 개를 이용해 특정 패키지로 지정 (basePackages 를 type-safe 하게 사용하기 위해 제공)
//                       쉽게 말해 해당 클래스가 담긴 패키지
// assignableTypes : 적용 범위를 특정 클래스(타입)에 할당할 수 있는 컨트롤러로 지정
// annotations : 적용 범위를 특정 애노테이션을 사용한 컨트롤러로 지정
@ControllerAdvice // 전체 컨트롤러를 감시
public class BaseExceptionHandler {

    // GeneralException 전용
    @ExceptionHandler
    public ModelAndView general(GeneralException e) {
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = errorCode.isClientSideError() ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
        return new ModelAndView("error", Map.of(
                "statusCode", status.value(),
                "errorCdoe", errorCode,
                "message", errorCode.getMessage(e) // getReasonPhrase() : 상태에 대한 응답 메시지 문자열을 확인
        ), status); // ModelAndView 도 status 값을 가질 수 있다.
    }

    // 그 외 예상치 못한 모든 에러를 처리 (INTERNAL_ERROR 로 간주)
    @ExceptionHandler
    public ModelAndView exception(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ModelAndView("error", Map.of(
                "statusCode", status.value(),
                "errorCdoe", errorCode,
                "message", errorCode.getMessage(e) // getReasonPhrase() : 상태에 대한 응답 메시지 문자열을 확인
        ), status); // ModelAndView 도 status 값을 가질 수 있다.
    }
}

