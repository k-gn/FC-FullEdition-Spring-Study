package com.example.practice.service;

import com.example.practice.dto.AniDto;
import com.example.practice.model.Ani;

import java.util.List;

public interface AniService {

    // 애니 목록 조회
    List<AniDto> findAll();

    // 애니 조회
    AniDto findById(Long id);
    AniDto findByTitle(String title);

    // 애니 등록
    Long save(AniDto ani);

    // 애니 수정
    Long update(Long id, AniDto aniDto);

    // 애니 삭제
    Long delete(Long id);
}
