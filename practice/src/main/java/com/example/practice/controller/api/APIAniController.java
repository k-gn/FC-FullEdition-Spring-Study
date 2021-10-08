package com.example.practice.controller.api;

import com.example.practice.dto.APIDataResponse;
import com.example.practice.dto.AniDto;
import com.example.practice.service.AniService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/anies")
@RequiredArgsConstructor
public class APIAniController {

    private final AniService aniService;

    @GetMapping
    public APIDataResponse<?> findAll() {
        return APIDataResponse.of(aniService.findAll());
    }

    @GetMapping("/{id}")
    public APIDataResponse<?> findById(@PathVariable Long id) {
        return APIDataResponse.of(aniService.findById(id));
    }

}
