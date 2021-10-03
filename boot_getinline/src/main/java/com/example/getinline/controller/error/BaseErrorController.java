package com.example.getinline.controller.error;

import com.example.getinline.constant.ErrorCode;
import com.example.getinline.dto.APIErrorResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class BaseErrorController implements ErrorController {
    // 공통 에러 페이지
    // 에러 발생 시 기본으로 처리할 ErrorController, 따로 핸들링하지 않을 경우 넘어옴
    // 미리 정해놓은 에러가 아닌 ErrorController 통해 온 에러는 stacktrace 가 등장함
    
    @RequestMapping(value = "/error", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletResponse response) {
        HttpStatus status = HttpStatus.valueOf(response.getStatus());
        ErrorCode errorCode = status.is4xxClientError() ? ErrorCode.BAD_REQUEST : ErrorCode.INTERNAL_ERROR;
        return new ModelAndView("error", Map.of(
                "statusCode", status.value(),
                "errorCdoe", errorCode,
                "message", errorCode.getMessage(status.getReasonPhrase()) // getReasonPhrase() : 상태에 대한 응답 메시지 문자열을 확인
        ), status); // ModelAndView 도 status 값을 가질 수 있다.
    }

    @RequestMapping("/error")
    public ResponseEntity<APIErrorResponse> error(HttpServletResponse response) {
        System.out.println("BaseErrorController api");
        HttpStatus status = HttpStatus.valueOf(response.getStatus());
        ErrorCode errorCode = status.is4xxClientError() ? ErrorCode.BAD_REQUEST : ErrorCode.INTERNAL_ERROR;
        return ResponseEntity
                .status(status)
                .body(APIErrorResponse.of(false, errorCode));
    }
}
