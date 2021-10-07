package com.example.practice.service;

import com.example.practice.constant.Genre;
import com.example.practice.dto.AniDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AniServiceImplTest {

    @Autowired
    private AniService aniService;

    @Test
    void save() {
        Long id1 = aniService.save(
                AniDto.builder()
                        .title("title")
                        .content("content")
                        .genre(Genre.SPORTS)
                        .build()
        );

//        Long id2 = aniService.save(
//                AniDto.builder()
//                        .title("title")
//                        .content("content")
//                        .genre(Genre.SPORTS)
//                        .build()
//        );

//        assertThat(id).isEqualTo(3L);
    }

    @Test
    void findAll() {
        aniService.findAll().forEach(System.out::println);
    }

    @Test
    void findById() {
        AniDto ani = aniService.findById(1L);
        System.out.println(ani);
    }

    @Test
    void update() {
        aniService.update(1L, AniDto.builder()
                .title("UPDATE title")
                .content("content")
                .genre(Genre.SPORTS)
                .build());
    }

    @Test
    void delete() {
        aniService.delete(1L);
    }
}