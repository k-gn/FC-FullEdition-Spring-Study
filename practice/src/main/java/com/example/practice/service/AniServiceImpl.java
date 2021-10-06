package com.example.practice.service;

import com.example.practice.controller.error.exception.GeneralException;
import com.example.practice.dto.AniDto;
import com.example.practice.model.Ani;
import com.example.practice.repository.AniRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AniServiceImpl implements AniService {

    private final AniRepository aniRepository;

    @Override
    public List<AniDto> findAll() {

        return aniRepository.findAll().stream()
                .map(entity -> AniDto.entityToDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public AniDto findById(Long id) {

        return aniRepository.findById(id)
                .map(entity -> AniDto.entityToDto(entity))
                .orElseThrow(() -> new GeneralException("해당 애니메이션이 없습니다."));
    }

    @Override
    public AniDto findByTitle(String title) {
        return null;
    }

    @Override
    public Long save(AniDto aniDto) {
        Ani ani = AniDto.dtoToEntity(aniDto);
        aniRepository.save(ani);
        return ani.getId();
    }

    @Override
    public Long update(Long id, AniDto aniDto) {
        Ani ani = AniDto.dtoToEntity(aniDto);
        return aniRepository.update(id, ani);
    }

    @Override
    public Long delete(Long id) {
        return aniRepository.delete(id);
    }
}
