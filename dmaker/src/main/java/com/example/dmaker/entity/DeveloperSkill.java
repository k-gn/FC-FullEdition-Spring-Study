package com.example.dmaker.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DeveloperSkill {
    
    BACK_END("백엔드 개발자"),
    FRONT_END("프론트엔드 개발자"),
    FULL_STACK("풀스택 개발자");
    
    private final String description;
}
