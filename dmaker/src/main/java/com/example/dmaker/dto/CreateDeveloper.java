package com.example.dmaker.dto;

import com.example.dmaker.entity.Developer;
import com.example.dmaker.util.DeveloperLevel;
import com.example.dmaker.util.DeveloperSkill;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateDeveloper {

    // 이런식의 구조는 개발자 취향

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Request {
        @NotNull
        private DeveloperLevel developerLevel;
        @NotNull
        private DeveloperSkill developerSkill;
        @NotNull
        @Min(0)
        @Max(20)
        private Integer experienceYears;

        @NotNull
        @Size(min = 3, max = 50, message = "memberId size must 3-50")
        private String memberId;
        @NotNull
        @Size(min = 3, max = 20, message = "memberId size must 3-20")
        private String name;
        @Min(18)
        private Integer age;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private DeveloperLevel developerLevel;
        private DeveloperSkill developerSkill;
        private Integer experienceYears;
        private String memberId;

        // entity 로 부터 Response 객체 생성
        public static Response fromEntity(@NonNull Developer developer) {
            return Response.builder()
                    .developerSkill(developer.getDeveloperSkill())
                    .developerLevel(developer.getDeveloperLevel())
                    .experienceYears(developer.getExperienceYears())
                    .memberId(developer.getMemberId())
                    .build();
        }
    }
}
