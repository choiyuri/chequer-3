package com.chequer.service;

import com.chequer.domain.member.Member;
import com.chequer.domain.member.MemberRepository;
import com.chequer.domain.member.MemberResponseDto;
import com.chequer.domain.member.MemberSaveRequestDto;
import com.chequer.exception.BaseException;
import com.chequer.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberResponseDto save(MemberSaveRequestDto requestDto) {

        // 이미 존재하는 이메일계정이라면
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new BaseException(ErrorCode.E1001);
        }

        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        Member member = userRepository.save(requestDto.toEntity());
        return new MemberResponseDto(member);
    }
}
