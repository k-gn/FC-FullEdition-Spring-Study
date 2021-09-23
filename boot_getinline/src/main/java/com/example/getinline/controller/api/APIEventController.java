package com.example.getinline.controller.api;

import com.example.getinline.constant.EventStatus;
import com.example.getinline.dto.APIDataResponse;
import com.example.getinline.dto.EventRequest;
import com.example.getinline.dto.EventResponse;
import com.example.getinline.service.EventServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

// @Validated
// 메소드 파라미터에 직접 애노테이션을 사용한 검증 시 필요
// ConstraintViolationException 예외를 발생 시킨다. (직접 처리해줘야 하는 예외)
// @ConfigurationProperties 클래스에도 적용 가능
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class APIEventController {

    private final EventServiceImpl eventService;

    // validation : 애노테이션 기반으로 데이터 검증을 돕는 방식이 도입됨
    // 검증 구현과 비즈니스 로직을 분리하고, 비즈니스 로직에 더 집중 가능
    // 간결하고 가독성이 좋음
    @GetMapping("/events")
    public APIDataResponse<List<EventResponse>> getEvents(
            @Positive Long placeId, // 양수인지?
            @Size(min = 2) String eventName, // 최소 2글자 인지?
            EventStatus eventStatus,
            // String 으로 들어간 LocalDateTime 값을 변환시켜줌
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventStartDatetime,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventEndDatetime
    ) {
        // 흔히 하는 방식으로 map 을 사용할 경우
        // 원하는 json body 를 내보낼 순 있지만
        // 받는 입장에선 펼쳐보기 전까진 어떤 key 들이 있는지 모른다. (추가 로직이 발생)
        // 따라서 응답 데이터 타입을 따로 설계해서 보내주는 것이 좋다. (필드들이 정확히 명시되어 있어서 쉽게 확인가능.)
//        Map<Object, String> map = new HashMap<>();
//        map.put("key1", "value1");
        List<EventResponse> eventResponses = eventService.getEvents(
                placeId,
                eventName,
                eventStatus,
                eventStartDatetime,
                eventEndDatetime
        ).stream().map(EventResponse::from).toList();

        return APIDataResponse.of(eventResponses);
    }

    // @Valid
    // 검증하려는 데이터 오브젝트에 검증 로직을 적용할 때
    // MethodArgumentNotValidException 예외 발생 (ResponseEntityExceptionHandler 지원을 받을 수 있다.)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/events")
    public APIDataResponse<String> createEvent(@Valid @RequestBody EventRequest eventRequest) {
        boolean result = eventService.createEvent(eventRequest.toDTO());

        return APIDataResponse.of(Boolean.toString(result));
    }

    @GetMapping("/events/{eventId}")
    public APIDataResponse<EventResponse> getEvent(@Positive @PathVariable Long eventId) {
//        if (eventId.equals(2L)) {
//            return APIDataResponse.empty();
//        }
//
//        return APIDataResponse.of(EventResponse.of(
//                1L,
//                "오후 운동",
//                EventStatus.OPENED,
//                LocalDateTime.of(2021, 1, 1, 13, 0, 0),
//                LocalDateTime.of(2021, 1, 1, 16, 0, 0),
//                0,
//                24,
//                "마스크 꼭 착용하세요"
//        ));
        EventResponse eventResponse = EventResponse.from(eventService.getEvent(eventId).orElse(null));

        return APIDataResponse.of(eventResponse);
    }

    @PutMapping("/events/{eventId}")
    public APIDataResponse<String> modifyEvent(
            @Positive @PathVariable Long eventId,
            @Valid @RequestBody EventRequest eventRequest
    ) {
        boolean result = eventService.modifyEvent(eventId, eventRequest.toDTO());
        return APIDataResponse.of(Boolean.toString(result));
    }

    @DeleteMapping("/events/{eventId}")
    public APIDataResponse<String> removeEvent(@Positive @PathVariable Long eventId) {
        boolean result = eventService.removeEvent(eventId);

        return APIDataResponse.of(Boolean.toString(result));
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
