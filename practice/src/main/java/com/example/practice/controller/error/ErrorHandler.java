package com.example.practice.controller.error;

import com.example.practice.constant.ErrorCode;
import com.example.practice.controller.error.exception.CustomException;
import com.example.practice.controller.error.exception.GeneralException;
import com.example.practice.dto.APIErrorResponse;
import com.example.practice.util.Script;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.util.Map;

@RestController
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<Object> general(GeneralException e) {
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = errorCode.isClientSideError() ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity
                .status(status)
                .body(APIErrorResponse.of(false, errorCode, errorCode.getMessage(e)));
    }

    @ExceptionHandler(BindException.class)
    public String validation(BindException e) {
        return Script.back("입력값이 올바르지 않습니다.");
    }

    @ExceptionHandler(CustomException.class)
    public String confirm(CustomException e) {
        return Script.confirm(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(APIErrorResponse.of(false, errorCode, errorCode.getMessage(e)));
    }
}
