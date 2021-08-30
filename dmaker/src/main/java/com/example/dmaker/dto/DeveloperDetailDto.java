package com.example.dmaker.dto;

import com.example.dmaker.code.StatusCode;
import com.example.dmaker.entity.Developer;
import com.example.dmaker.util.DeveloperLevel;
import com.example.dmaker.util.DeveloperSkill;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class DeveloperDetailDto {

    private DeveloperLevel developerLevel;
    private DeveloperSkill developerSkill;
    private StatusCode statusCode;
    private Integer experienceYears;
    private String memberId;
    private String name;
    private Integer age;

    // entity 로 부터 dto 생성
    public static DeveloperDetailDto fromEntity(Developer developer) {
        System.out.println("developer : " + developer);
        return DeveloperDetailDto.builder()
                .developerLevel(developer.getDeveloperLevel())
                .developerSkill(developer.getDeveloperSkill())
                .experienceYears(developer.getExperienceYears())
                .statusCode(developer.getStatusCode())
                .memberId(developer.getMemberId())
                .name(developer.getName())
                .age(developer.getAge())
                .build();
    }
}
