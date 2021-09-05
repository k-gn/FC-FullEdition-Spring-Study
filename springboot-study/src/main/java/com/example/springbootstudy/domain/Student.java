package com.example.springbootstudy.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor // json 사용 시 기본생성자 필수
@AllArgsConstructor(staticName = "of")
@Data
public class Student { // implements Serializable { // Redis 사용 시 Serializable 필요

    private String name;
    private Integer age;
    private Grade grade;

    public enum Grade {
        A,B,C,D,F
    }
}
