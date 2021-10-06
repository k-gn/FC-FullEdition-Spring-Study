package com.example.practice.service;

import com.example.practice.dto.MemberDto;
import com.example.practice.model.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    // 회원 가입
    Long save(MemberDto memberDto);

    // 회원 목록 조회
    List<MemberDto> findAll();

    // 회원 조회
    MemberDto findById(Long id);
    MemberDto findByEmail(String email);

    // 회원 정보 수정
    Long update(Long id, MemberDto memberDto);

    // 회원 삭제
    Long delete(Long id);
}
