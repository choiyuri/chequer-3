package com.chequer.web;

import com.chequer.domain.board.Board;
import com.chequer.domain.board.BoardRepository;
import com.chequer.domain.board.BoardSaveRequestDto;
import com.chequer.domain.board.BoardUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BoardApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private BoardRepository boardRepository;

    @After
    public void tearDown() throws Exception {
        boardRepository.deleteAll();
    }

    @Test
    public void boardSaveTest() throws Exception {

        // given
        String title = "title";
        String content = "content";
        String author = "author";
        BoardSaveRequestDto requestDto = BoardSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        String url = "http://localhost:" + port + "/api/v1/board";

        // when
        ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity(url, requestDto, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Board> boardList = boardRepository.findAll();
        assertThat(boardList.get(0).getTitle()).isEqualTo(title);
        assertThat(boardList.get(0).getContent()).isEqualTo(content);
        assertThat(boardList.get(0).getAuthor()).isEqualTo(author);
    }

    @Test
    public void boardUpdateTest() throws Exception {

        // given
        Board board = boardRepository.save(Board.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long id = board.getId();
        String title = "title1";
        String content = "content1";

        BoardUpdateRequestDto requestDto = BoardUpdateRequestDto.builder()
                .title(title)
                .content(content)
                .build();

        String url = "http://localhost:" + port + "/api/v1/board/" + id;

        HttpEntity<BoardUpdateRequestDto> requestDtoHttpEntity = new HttpEntity<>(requestDto);

        // when
        ResponseEntity<Long> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestDtoHttpEntity, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Optional<Board> boardById = boardRepository.findById(id);
        assertThat(boardById.get().getTitle()).isEqualTo(title);
        assertThat(boardById.get().getContent()).isEqualTo(content);

    }

    @Test
    public void boardListTest() throws Exception {

        // given
        String url = "http://localhost:" + port + "/api/v1/board";



        // when


        // then


    }
}
