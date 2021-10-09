package com.example.practice.service;

import com.example.practice.constant.Auth;
import com.example.practice.controller.error.exception.GeneralException;
import com.example.practice.dto.MemberDto;
import com.example.practice.model.Member;
import com.example.practice.repository.MemberRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRespository memberRespository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Long save(MemberDto memberDto) {
        Member member = MemberDto.dtoToEntity(memberDto);

        String encPwd = passwordEncoder.encode(member.getPassword());
        member.setPassword(encPwd);
        member.setAuth(memberDto.isCheck() ? Auth.ROLE_ADMIN : Auth.ROLE_USER);
        member.setProvider("home");

        memberRespository.save(member);

        return member.getId();
    }

    @Override
    public List<MemberDto> findAll() {
        List<Member> memberList = memberRespository.findAll();
        return memberList.stream()
                .map(entity -> MemberDto.entityToDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public MemberDto findById(Long id) {
        Member member = memberRespository.findById(id).orElseThrow(() -> new GeneralException("없는 사용자 입니다."));
        return MemberDto.entityToDto(member);
    }

    @Override
    public MemberDto findByEmail(String email) {
        Member member = memberRespository.findByEmail(email);
        if (member == null) throw new GeneralException("없는 사용자 입니다.");
        return MemberDto.entityToDto(member);
    }

    @Override
    public Long update(Long id, MemberDto memberDto) {
        Member member = MemberDto.dtoToEntity(memberDto);
        member.setId(id);
        return memberRespository.update(member);
    }

    @Override
    public Long delete(Long id) {
        return memberRespository.delete(id);
    }
}
