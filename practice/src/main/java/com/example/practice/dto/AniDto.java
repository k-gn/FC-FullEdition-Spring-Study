package com.example.practice.dto;

import com.example.practice.constant.Genre;
import com.example.practice.model.Ani;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AniDto {

    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
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
