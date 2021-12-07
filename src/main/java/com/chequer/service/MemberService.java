package com.chequer.service;

import com.chequer.domain.user.MemberResponseDto;
import com.chequer.domain.user.MemberSaveRequestDto;

public interface MemberService {

    MemberResponseDto findByEmail(String email);

    MemberResponseDto save(MemberSaveRequestDto requestDto);
}
