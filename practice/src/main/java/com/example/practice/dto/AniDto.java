package com.example.practice.dto;

import com.example.practice.constant.Genre;
import com.example.practice.model.Ani;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AniDto {

    private Long id;

    private String title;

    private String content;

    private Genre genre;

    public static Ani dtoToEntity(AniDto dto) {

        return Ani.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .genre(dto.getGenre())
                .build();
    }

    public static AniDto entityToDto(Ani ani) {

        return AniDto.builder()
                .id(ani.getId())
                .title(ani.getTitle())
                .content(ani.getContent())
                .genre(ani.getGenre())
                .build();
    }
}
