package com.example.dmaker.dto;

import com.example.dmaker.util.DeveloperLevel;
import com.example.dmaker.util.DeveloperSkill;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class EditDeveloper {

    // 이런식의 구조는 개발자 취향

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {

        @NotNull
        private DeveloperLevel developerLevel;
        @NotNull
        private DeveloperSkill developerSkill;
        @NotNull
        @Min(0)
        @Max(20)
        private Integer experienceYears;

    }

}
