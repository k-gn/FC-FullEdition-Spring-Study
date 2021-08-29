package com.example.dmaker.dto;

import lombok.*;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;


@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@Builder
@Slf4j
//@UtilityClass // 유틸리티 클래스에 적용하는 어노테이션. @UtilityClass 은 기본생성자가 private 으로 생성
//@Data
public class DevDto {

    private String name;
    private int age;

    @NonNull
    private String id;

}
