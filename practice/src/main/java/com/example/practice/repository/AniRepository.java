package com.example.practice.repository;

import com.example.practice.model.Ani;
import com.example.practice.model.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AniRepository {
    
    // 애니 목록 조회
    List<Ani> findAll();

    // 애니 조회
    Optional<Ani> findById(Long id);
    Optional<Ani> findByTitle(String title);

    // 애니 등록
    Long save(Ani ani);

    // 애니 수정
    Long update(Long id, Ani ani);

    // 애니 삭제
    Long delete(Long id);




}
