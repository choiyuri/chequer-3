package com.chequer.web.controller;


import com.chequer.domain.auth.LoginDto;
import com.chequer.domain.member.MemberRepository;
import com.chequer.domain.member.MemberSaveRequestDto;
import com.chequer.exception.ErrorCode;
import com.chequer.web.controller.common.BaseControllerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthApiControllerTest extends BaseControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String FIRST_NAME = "first";
    private static final String LAST_NAME = "last";
    private static final String EMAIL = "email@gmail.com";
    private static final String PASSWORD = "1234";

    @AfterEach
    public void afterEach() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("1. 사용자 인증 성공")
    public void authSuccess() throws Exception {

        // given
        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .build()
                .setPassword(passwordEncoder.encode(PASSWORD));

        memberRepository.save(requestDto.toEntity());

        LoginDto loginDto = LoginDto.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        // when
        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto))
                .accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)
                .characterEncoding(StandardCharsets.UTF_8);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("2. 사용자 인증 실패 - 이메일 불일치")
    public void authFailEmail() throws Exception {

        // given
        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build()
                .setPassword(passwordEncoder.encode(PASSWORD));

        memberRepository.save(requestDto.toEntity());

        LoginDto loginDto = LoginDto.builder()
                .email("EMAIL_FAIL@gmail.com")
                .password(PASSWORD)
                .build();

        // when
        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto))
                .accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)
                .characterEncoding(StandardCharsets.UTF_8);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value(ErrorCode.E1002.name()));

    }

    @Test
    @DisplayName("3. 사용자 인증 실패 - 패스워드 불일치")
    public void authFailPassword() throws Exception {

        // given
        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build()
                .setPassword(passwordEncoder.encode(PASSWORD));

        memberRepository.save(requestDto.toEntity());

        LoginDto loginDto = LoginDto.builder()
                .email(EMAIL)
                .password("PASSWORD_FAIL")
                .build();

        // when
        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto))
                .accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)
                .characterEncoding(StandardCharsets.UTF_8);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value(ErrorCode.E3005.name()));

    }
}
