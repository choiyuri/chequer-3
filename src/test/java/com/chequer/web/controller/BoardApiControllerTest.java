package com.chequer.web.controller;

import com.chequer.domain.auth.CustomUserDetail;
import com.chequer.domain.board.Board;
import com.chequer.domain.board.BoardRepository;
import com.chequer.domain.board.BoardSaveRequestDto;
import com.chequer.domain.board.BoardUpdateRequestDto;
import com.chequer.exception.ErrorCode;
import com.chequer.web.controller.common.BaseControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BoardApiControllerTest extends BaseControllerTest {

    @Autowired
    private BoardRepository boardRepository;

    /**
     * Mock Request 에 사용될 유저1 생성
     *
     * @return 유저1
     */
    private static CustomUserDetail getMockUser1() {
        return new CustomUserDetail(1L,
                "email1@gmail.com",
                "1234",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
    }

    /**
     * Mock Request 에 사용될 유저2 생성
     *
     * @return 유저2
     */
    private static CustomUserDetail getMockUser2() {
        return new CustomUserDetail(2L,
                "email2@gmail.com",
                "5678",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
    }

    /**
     * Board 생성 (author=User1)
     *
     * @return Board
     */
    private static Board getBoardForUser1() {
        return Board.builder()
                .title("title")
                .content("content")
                .author(getMockUser1().getUserId())
                .deleteYn(Boolean.FALSE)
                .build();
    }

    @AfterEach
    public void afterEach() {
        boardRepository.deleteAll();
    }

    @Test
    @DisplayName("1. 게시글 등록 성공한다")
    public void testSaveSuccess() throws Exception {

        // given
        String title = "title";
        String content = "content";
        BoardSaveRequestDto requestDto = BoardSaveRequestDto.builder()
                .title(title)
                .content(content)
                .build();

        // when
        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/board")
                .with(user(getMockUser1()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto));

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated());

        List<Board> all = boardRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("2. 게시글 수정 성공한다")
    public void testUpdateSuccess() throws Exception {

        // given
        Board savedBoard = boardRepository.save(getBoardForUser1());
        Long updateId = savedBoard.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        BoardUpdateRequestDto requestDto = BoardUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        // when
        MockHttpServletRequestBuilder requestBuilder = put("/api/v1/board/" + updateId)
                .with(user(getMockUser1()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto));

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Board> updatedBoard = boardRepository.findById(updateId);
        assertThat(updatedBoard.isPresent()).isTrue();
        assertThat(updatedBoard.get().getTitle()).isEqualTo(expectedTitle);
        assertThat(updatedBoard.get().getContent()).isEqualTo(expectedContent);
    }

    @Test
    @DisplayName("3. 게시글 수정 실패한다 - 타인 게시글 수정 불가")
    public void testUpdateFailOthers() throws Exception {

        // given
        Board savedBoard = boardRepository.save(getBoardForUser1());
        Long updateId = savedBoard.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        BoardUpdateRequestDto requestDto = BoardUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        // when
        MockHttpServletRequestBuilder requestBuilder = put("/api/v1/board/" + updateId)
                .with(user(getMockUser2()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto));

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error").value(ErrorCode.E1003.name()));
    }

    @Test
    @DisplayName("4. 게시글 수정 실패한다 - 없는 게시글 ID")
    public void testUpdateFailNon() throws Exception {

        // given
        long updateId = 99999L;
        String expectedTitle = "title9";
        String expectedContent = "content9";

        BoardUpdateRequestDto requestDto = BoardUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        // when
        MockHttpServletRequestBuilder requestBuilder = put("/api/v1/board/" + updateId)
                .with(user(getMockUser2()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto));

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value(ErrorCode.E2001.name()));
    }

    @Test
    @DisplayName("5. 게시글 삭제 성공한다")
    public void testDeleteSuccess() throws Exception {

        // given
        Board savedBoard = boardRepository.save(getBoardForUser1());
        Long deleteId = savedBoard.getId();

        // when
        MockHttpServletRequestBuilder requestBuilder = delete("/api/v1/board/" + deleteId)
                .with(user(getMockUser1()));

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Board> deletedBoard = boardRepository.findById(deleteId);
        assertThat(deletedBoard.isPresent()).isTrue();
        assertThat(deletedBoard.get().getDeleteYn()).isTrue();
    }

    @Test
    @DisplayName("6. 게시글 삭제 실패한다 - 타인 게시글 수정 불가")
    public void testDeleteFailOthers() throws Exception {

        // given
        Board savedBoard = boardRepository.save(getBoardForUser1());
        Long deleteId = savedBoard.getId();

        // when
        MockHttpServletRequestBuilder requestBuilder = delete("/api/v1/board/" + deleteId)
                .with(user(getMockUser2()));

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error").value(ErrorCode.E1003.name()));

        Optional<Board> deletedBoard = boardRepository.findById(deleteId);
        assertThat(deletedBoard.isPresent()).isTrue();
        assertThat(deletedBoard.get().getDeleteYn()).isFalse();
    }

    @Test
    @DisplayName("7. 게시글 삭제 실패한다 - 없는 게시글 ID")
    public void testDeleteFailNon() throws Exception {

        // given
        long deleteId = 99999L;

        // when
        MockHttpServletRequestBuilder requestBuilder = delete("/api/v1/board/" + deleteId)
                .with(user(getMockUser2()));

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value(ErrorCode.E2001.name()));
    }

    @Test
    @DisplayName("8. 게시글 목록 조회된다 - totalCount 확인")
    public void testList() throws Exception {

        // given
        int count = 20;
        List<Board> saveList = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            saveList.add(Board.builder()
                    .title("title")
                    .content("content")
                    .author(getMockUser1().getUserId())
                    .deleteYn(Boolean.FALSE)
                    .build());
        }
        boardRepository.saveAll(saveList);

        // when
        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/board")
                .with(user(getMockUser1()));

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.totalCount").value(count));
    }

    @Test
    @DisplayName("9. 게시글 조회수 증가한다 - 타인")
    public void testIncreaseHitOthers() throws Exception {

        // given
        Board savedBoard = boardRepository.save(getBoardForUser1());
        Long selectId = savedBoard.getId();

        // when
        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/board/" + selectId)
                .with(user(getMockUser2()));

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Board> selectBoard = boardRepository.findById(selectId);
        assertThat(selectBoard.isPresent()).isTrue();
        assertThat(selectBoard.get().getHits()).isEqualTo(savedBoard.getHits() + 1);
    }

    @Test
    @DisplayName("10. 게시글 조회수 증가하지 않는다 - 본인")
    public void testIncreaseHitMe() throws Exception {

        // given
        Board savedBoard = boardRepository.save(getBoardForUser1());
        Long selectId = savedBoard.getId();

        // when
        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/board/" + selectId)
                .with(user(getMockUser1()));

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Board> selectBoard = boardRepository.findById(selectId);
        assertThat(selectBoard.isPresent()).isTrue();
        assertThat(selectBoard.get().getHits()).isEqualTo(savedBoard.getHits());
    }
}
