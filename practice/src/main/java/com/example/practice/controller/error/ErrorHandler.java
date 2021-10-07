package com.example.practice.controller.error;

import com.example.practice.constant.ErrorCode;
import com.example.practice.controller.error.exception.GeneralException;
import com.example.practice.util.Script;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.util.Map;

@ControllerAdvice
public class ErrorHandler {

    // GeneralException 전용
    @ExceptionHandler
    public ModelAndView general(GeneralException e) {
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = errorCode.isClientSideError() ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
        return new ModelAndView("error", Map.of(
                "statusCode", status.value(),
                "errorCdoe", errorCode,
                "message", errorCode.getMessage(e)
        ), status);
    }


    // 그 외 예상치 못한 모든 에러를 처리 (INTERNAL_ERROR 로 간주)
    @ExceptionHandler
    public ModelAndView exception(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ModelAndView("error", Map.of(
                "statusCode", status.value(),
                "errorCdoe", errorCode,
                "message", errorCode.getMessage(e)
        ), status);
    }
}
