package com.example.practice.repository;

import com.example.practice.model.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberRespository {
    
    // 회원 가입
    Long save(Member member);
    
    // 회원 목록 조회
    List<Member> findAll();
    
    // 회원 조회
    Member findById(Long id);
    Member findByEmail(String email);

    // 회원 정보 수정
    Long update(Long id, Member member);

    // 회원 삭제
    Long delete(Long id);
}
