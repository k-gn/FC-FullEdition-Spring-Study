package com.example.oauth2practice.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFindService {

    private final UserRepository userRepository;

    public boolean existsByEmail(final String email) {
        return userRepository.existsByEmail(email);
    }

}
