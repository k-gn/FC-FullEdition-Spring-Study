package com.example.dmaker.dto;

import com.example.dmaker.entity.Developer;
import com.example.dmaker.util.DeveloperLevel;
import com.example.dmaker.util.DeveloperSkill;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class DeveloperDto {

    private DeveloperLevel developerLevel;
    private DeveloperSkill developerSkill;
    private String memberId;

    // entity 로 부터 dto 생성
    public static DeveloperDto fromEntity(Developer developer) {
        return DeveloperDto.builder()
                .developerLevel(developer.getDeveloperLevel())
                .developerSkill(developer.getDeveloperSkill())
                .memberId(developer.getMemberId())
                .build();
    }
}
