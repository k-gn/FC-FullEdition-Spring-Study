package com.example.practice.config.auth;

import com.example.practice.controller.error.exception.CustomException;
import com.example.practice.controller.error.exception.GeneralException;
import com.example.practice.model.Member;
import com.example.practice.repository.MemberRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRespository memberRespository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRespository.findByEmail(username);
        return new PrincipalDetails(member);
    }
}
