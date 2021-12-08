package com.chequer.web.controller;

import com.chequer.domain.board.BoardResponseDto;
import com.chequer.domain.board.BoardSaveRequestDto;
import com.chequer.domain.board.BoardUpdateRequestDto;
import com.chequer.service.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@Api(tags = "게시글 API")
@RequestMapping("/api/v1")
public class BoardApiController {

    private final BoardService boardService;

    @ApiOperation("게시글 작성")
    @PostMapping("/board")
    public ResponseEntity<BoardResponseDto> save(@RequestBody BoardSaveRequestDto requestDto) {
        return ResponseEntity.ok(boardService.save(requestDto));
    }

    @ApiOperation("게시글 수정")
    @PutMapping("/board/{id}")
    public ResponseEntity<BoardResponseDto> update(@PathVariable Long id,
                                                   @RequestBody BoardUpdateRequestDto requestDto,
                                                   Principal principal) {
        return ResponseEntity.ok(boardService.update(id, requestDto, principal.getName()));
    }

    @ApiOperation("게시글 삭제")
    @DeleteMapping("/board/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id,
                       Principal principal) {
        return ResponseEntity.ok(boardService.delete(id, principal.getName()));
    }

    @ApiOperation("게시글 목록")
    @GetMapping("/board")
    public ResponseEntity<Page<BoardResponseDto>> list(@RequestParam(defaultValue = "0") Integer page,
                                                       @RequestParam(defaultValue = "10") Integer size) {

        return ResponseEntity.ok(boardService.list(PageRequest.of(page, size)));
    }

    @ApiOperation("게시글 본문 보기")
    @GetMapping("/board/{id}")
    public ResponseEntity<BoardResponseDto> view(@PathVariable Long id,
                                                 Principal principal) {

        return ResponseEntity.ok(boardService.view(id, principal.getName()));
    }

}
