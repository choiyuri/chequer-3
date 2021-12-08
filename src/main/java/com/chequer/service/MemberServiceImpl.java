package com.chequer.service;

import com.chequer.domain.user.Member;
import com.chequer.domain.user.MemberRepository;
import com.chequer.domain.user.MemberResponseDto;
import com.chequer.domain.user.MemberSaveRequestDto;
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
    public MemberResponseDto findByEmail(String email) {
        Member member = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(ErrorCode.E0002));
        return new MemberResponseDto(member);
    }

    @Override
    public MemberResponseDto save(MemberSaveRequestDto requestDto) {
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        Member member = userRepository.save(requestDto.toEntity());
        return new MemberResponseDto(member);
    }
}
