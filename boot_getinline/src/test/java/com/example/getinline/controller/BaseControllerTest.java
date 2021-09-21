package com.example.getinline.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// autowired 생략 가능 (비추 - 파라미터가 전부 스프링 관리하에 들어가서 스프링 주입 외에 내가 직접 주입하고 싶은 것들, 다른 파라미터 리졸버를 쓸 수 없다.)
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DisplayName("View 컨트롤러 - 기본 페이지")
//@SpringBootTest
@WebMvcTest(BaseController.class) // mvc web 슬라이싱 테스트 (AutoConfigureMockMvc 생략 가능)
//@AutoConfigureMockMvc // MockMvc 설정을 자동으로 해준다.
class BaseControllerTest {

    // JUnit5 부터 필드 주입 외에도 주입이 가능해졌다. (생성자, 메소드)
//    @Autowired
    private final MockMvc mvc;

    public BaseControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[view][GET] 기본 페이지 요청") // 테스트 메소드명을 지을 때의 불편함을 해소할 수 있음.
    @Test
    // 테스트 메소드명은 실제 메소드 명을 따오거나(testRoot), should 방식, gwt 방식 등 다양하다. (네이밍 컨벤션)
    // 어떤 상황에서 어떤걸 실행했을 때 무슨 결과가 일어나는지 의미를 전달하는지에 초점을 마춰 이름을 작성하는 것이 목표.
    // givenNothing_whenRequestingRootPage_thenReturnIndexPage
    void basePageShouldShowIndexPage() throws Exception {

        // given

        // when & then
        mvc.perform(get("/")) // 테스트 요청
            .andExpect(status().isOk()) // 상태 검증                              // 그냥 contentType 은 뒤에 charset 까지 있어야 통과됨
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML)) // 뷰 타입 검증 (contentTypeCompatibleWith : 해당 내용이 있는지 확인)
            .andExpect(content().string(containsString("Index.html"))) // 뷰 내용 검증
            .andExpect(view().name("index")) // 뷰 이름 검증
            .andDo(print()); // test 결과를 출력 (에러 여부와 상관없이 출력 - 초기 관찰에 좋다.)
    }
}