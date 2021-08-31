package com.example.dmaker.controller;

import com.example.dmaker.dto.DeveloperDto;
import com.example.dmaker.service.DeveloperService;
import com.example.dmaker.util.DeveloperLevel;
import com.example.dmaker.util.DeveloperSkill;
import net.bytebuddy.matcher.ElementMatchers;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DMakerController.class) // controller 관련 빈을 로딩
class DMakerControllerTest {

    // 웹 애플리케이션을 애플리케이션 서버에 배포하지 않고도 스프링 MVC의 동작을 재현할 수 있는 클래스.
    // 브라우저에서 요청과 응답을 의미하는 객체
    // 호출을 가상으로 만들어 줄 수 있다.
    @Autowired
    private MockMvc mockMvc;

    @MockBean // 가짜 빈 등록
    private DeveloperService developerService;

    protected MediaType contentType =
            new MediaType(MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),
                    StandardCharsets.UTF_8);

    @Test
    void getAllDevelopers() throws Exception {

        DeveloperDto juniorDeveloperDto = DeveloperDto.builder()
                .developerSkill(DeveloperSkill.BACK_END)
                .developerLevel(DeveloperLevel.JUNIOR)
                .memberId("memberId1")
                .build();

        DeveloperDto seniorDeveloperDto = DeveloperDto.builder()
                .developerSkill(DeveloperSkill.FRONT_END)
                .developerLevel(DeveloperLevel.SENIOR)
                .memberId("memberId2")
                .build();

        // Mocking
        given(developerService.getAllEmployedDevelopers())
                .willReturn(Arrays.asList(juniorDeveloperDto, seniorDeveloperDto));

        mockMvc.perform(get("/developers").contentType(contentType))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(
                                        // $(응답 json)의 배열 0번째 객체의 developerSkill 값
                    jsonPath("$.[0].developerSkill", is(DeveloperSkill.BACK_END.name())) // enum 일 경우 .name() 써줘야 비교 된다.
            );

    }
}