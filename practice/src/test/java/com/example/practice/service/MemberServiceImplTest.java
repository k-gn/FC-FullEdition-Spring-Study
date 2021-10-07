package com.example.practice.service;

import com.example.practice.dto.MemberDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceImplTest {

    @Autowired
    private MemberService memberService;

    @Test
    void findOne() {
        System.out.println(memberService.findById(1L));
    }

    @Test
    void save() {
        memberService.save(MemberDto.builder()
                .email("test")
                .password("1234")
                .check(false)
                .build());
    }
}