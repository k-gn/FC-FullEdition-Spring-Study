package com.example.practice.controller.error;

import com.example.practice.constant.ErrorCode;
import com.example.practice.controller.error.exception.GeneralException;
import com.example.practice.dto.APIErrorResponse;
import com.example.practice.util.Script;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class APIErrorHandler {

    @ExceptionHandler
    public ResponseEntity<Object> general(GeneralException e) {
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = errorCode.isClientSideError() ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity
                .status(status)
                .body(APIErrorResponse.of(false, errorCode, errorCode.getMessage(e)));
    }

    @ExceptionHandler
    public String validation(BindException e) {
        return Script.back("입력값이 올바르지 않습니다.");
    }


    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(APIErrorResponse.of(false, errorCode, errorCode.getMessage(e)));
    }
}
