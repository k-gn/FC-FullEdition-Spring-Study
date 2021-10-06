package com.example.practice.controller.api;

import com.example.practice.dto.APIDataResponse;
import com.example.practice.dto.AniDto;
import org.springframework.web.bind.annotation.*;

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
    public APIDataResponse<?> register(AniDto aniDto) {
        return null;
    }

    @PutMapping("/{id}")
    public APIDataResponse<?> modify(@PathVariable Long id, AniDto aniDto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public APIDataResponse<?> remove(@PathVariable Long id) {
        return null;
    }
}
