package com.example.getinline.controller.api;

import com.example.getinline.constant.ErrorCode;
import com.example.getinline.dto.APIDataResponse;
import com.example.getinline.dto.APIErrorResponse;
import com.example.getinline.exception.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class APIEventController {

    @GetMapping("/events")
    public APIDataResponse<String> getEvents() {
        // 흔히 하는 방식으로 map 을 사용할 경우
        // 원하는 json body 를 내보낼 순 있지만
        // 받는 입장에선 펼쳐보기 전까진 어떤 key 들이 있는지 모른다. (추가 로직이 발생)
        // 따라서 응답 데이터 타입을 따로 설계해서 보내주는 것이 좋다. (필드들이 정확히 명시되어 있어서 쉽게 확인가능.)
//        Map<Object, String> map = new HashMap<>();
//        map.put("key1", "value1");
//        throw new GeneralException("테스트 메시지");
        APIDataResponse<String> response = APIDataResponse.of(null);
        return response;
    }

    @PostMapping("/events")
    public Boolean createEvent() throws HttpRequestMethodNotSupportedException {
//        throw new RuntimeException("runtime 테스트 메시지");
//        throw new HttpRequestMethodNotSupportedException("POST");
        return true;
    }

    @GetMapping("/events/{eventId}")
    public String getEvent(@PathVariable Integer eventId) {
        return "event " + eventId;
    }

    @PutMapping("/events/{eventId}")
    public Boolean modifyEvent(@PathVariable Integer eventId) {
        return true;
    }

    @DeleteMapping("/events/{eventId}")
    public Boolean removeEvent(@PathVariable Integer eventId) {
        return true;
    }

    // 특정 컨트롤러 내에서 에러 핸들링
//    @ExceptionHandler(GeneralException.class)
//    public ResponseEntity<APIErrorResponse> general(GeneralException e) {
//        // ResponseEntity : 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스 (HttpStatus, HttpHeaders, HttpBody를 포함)
//        // 그냥 데이터(body)를 리턴하는 ResponseBody 와 다르게 헤더와 상태코드를 직접 다루고 보낼 수 있다.
//        // ResponseBody 는 따로 필요없다.
//        ErrorCode errorCode = e.getErrorCode();
//        HttpStatus status = errorCode.isClientSideError() ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
//        return ResponseEntity
//                .status(status)
//                .body(APIErrorResponse.of(false, errorCode, errorCode.getMessage(e)));
//    }

    // 그냥 ResponseBody 만 사용한 에러 처리
//    @ExceptionHandler(GeneralException.class)
//    public APIErrorResponse generalBody(GeneralException e) {
//        ErrorCode errorCode = e.getErrorCode();
//        HttpStatus status = errorCode.isClientSideError() ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
//        return APIErrorResponse.of(false, errorCode, errorCode.getMessage(e));
//    }
}
