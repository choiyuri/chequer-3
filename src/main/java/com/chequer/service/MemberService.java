package com.chequer.service;

import com.chequer.domain.member.Member;
import com.chequer.domain.member.MemberSaveRequestDto;

public interface MemberService {

    Member save(MemberSaveRequestDto requestDto);

    Member findByEmail(String email);
}
