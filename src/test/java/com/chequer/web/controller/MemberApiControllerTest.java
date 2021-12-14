package com.chequer.web.controller;

import com.chequer.domain.member.MemberRepository;
import com.chequer.domain.member.MemberSaveRequestDto;
import com.chequer.exception.ErrorCode;
import com.chequer.web.controller.common.BaseControllerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberApiControllerTest extends BaseControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    private static final String FIRST_NAME = "first";
    private static final String LAST_NAME = "last";
    private static final String EMAIL = "email@gmail.com";
    private static final String PASSWORD = "1234";

    @AfterEach
    public void afterEach() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("1. 사용자 생성 성공")
    public void signupSuccess() throws Exception {

        // given
        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        // when
        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/signup")
                .content(objectMapper.writeValueAsString(requestDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.email").value(EMAIL));

    }

    @Test
    @DisplayName("2. 사용자 생성 실패 - 이메일 중복")

    public void signupFailDuplicate() throws Exception {

        // given
        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        memberRepository.save(requestDto.toEntity());

        // when
        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/signup")
                .content(objectMapper.writeValueAsString(requestDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value(ErrorCode.E1001.name()));

    }

    @Test
    @DisplayName("3. 사용자 생성 실패 - 이메일 누락")
    public void signupFailRequiredEmail() throws Exception {

        // given
        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .password(PASSWORD)
                .build();

        // when
        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/signup")
                .content(objectMapper.writeValueAsString(requestDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value(ErrorCode.E0001.name()));

    }

    @Test
    @DisplayName("4. 사용자 생성 실패 - 패스워드 누락")
    public void signupFailRequiredPassword() throws Exception {

        // given
        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .build();

        // when
        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/signup")
                .content(objectMapper.writeValueAsString(requestDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value(ErrorCode.E0001.name()));

    }

}
