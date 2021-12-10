package com.chequer.web.controller;

import com.chequer.domain.auth.LoginDto;
import com.chequer.domain.member.MemberRepository;
import com.chequer.domain.member.MemberSaveRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureDataJpa
@AutoConfigureMockMvc
@ActiveProfiles(value = "local")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MemberApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String FIRST_NAME = "first";
    private static final String LAST_NAME = "last";
    private static final String EMAIL = "email@gmail.com";
    private static final String PASSWORD = "1234";

    @Test
    @DisplayName("1. 사용자 생성 성공")
    public void signupSuccess() throws Exception {

        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/signup")
                .content(objectMapper.writeValueAsString(requestDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.result").value("SUCCESS"));
    }

    @Test
    @DisplayName("2. 사용자 생성 실패 - 이메일 중복")
    public void signupFailDuplicate() throws Exception {
        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/signup")
                .content(objectMapper.writeValueAsString(requestDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);

        memberRepository.save(requestDto.toEntity());

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result").value("FAIL"));
    }

    @Test
    @DisplayName("3. 사용자 생성 실패 - 필수정보 누락")
    public void signupFailRequired() {

    }

    @Test
    @DisplayName("4. 사용자 인증 성공")
    public void authSuccess() throws Exception {

        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        memberRepository.save(requestDto.toEntity());

        LoginDto loginDto = LoginDto.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto))
                .accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)
                .characterEncoding(StandardCharsets.UTF_8);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());

    }
}
