package com.chequer.service;

import com.chequer.domain.member.MemberResponseDto;
import com.chequer.domain.member.MemberSaveRequestDto;

public interface MemberService {

    MemberResponseDto save(MemberSaveRequestDto requestDto);
}
