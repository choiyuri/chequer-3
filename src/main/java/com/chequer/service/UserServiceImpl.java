package com.chequer.service;

import com.chequer.domain.auth.CustomUserDetail;
import com.chequer.domain.member.Member;
import com.chequer.domain.member.MemberRepository;
import com.chequer.exception.BaseException;
import com.chequer.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByEmail(username)
                .map(member -> createUser(member))
                .orElseThrow(() -> new BaseException(ErrorCode.E1002));
    }

    private CustomUserDetail createUser(Member member) {
        if (!member.getActivated()) {
            throw new BaseException(ErrorCode.E1004);
        }
        return new CustomUserDetail(member.getId(),
                member.getEmail(),
                member.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().getKey())));
    }

}
