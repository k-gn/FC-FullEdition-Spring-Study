package com.example.practice.controller.api;

import com.example.practice.dto.APIDataResponse;
import com.example.practice.dto.AniDto;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/anies")
public class APIAniController {

    @GetMapping
    public APIDataResponse<?> findAll() {
        return null;
    }

    @GetMapping("/{id}")
    public APIDataResponse<?> findById(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    public APIDataResponse<?> register(@Valid AniDto aniDto) {
        return null;
    }

    @PutMapping("/{id}")
    public APIDataResponse<?> modify(@PathVariable Long id, @Valid AniDto aniDto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public APIDataResponse<?> remove(@PathVariable Long id) {
        return null;
    }
}
