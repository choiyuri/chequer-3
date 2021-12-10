package com.chequer.web.controller;

import com.chequer.domain.auth.CustomUserDetail;
import com.chequer.domain.board.BoardSaveRequestDto;
import com.chequer.domain.board.BoardUpdateRequestDto;
import com.chequer.service.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@Api(tags = "게시글 API")
@RequestMapping("/api/v1")
public class BoardApiController {

    private final BoardService boardService;

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("게시글 작성")
    @PostMapping("/board")
    public Object save(@Valid @RequestBody BoardSaveRequestDto requestDto) {
        return boardService.save(requestDto);
    }

    @ApiOperation("게시글 수정")
    @PutMapping("/board/{id}")
    public Object update(@PathVariable Long id,
                         @Valid @RequestBody BoardUpdateRequestDto requestDto,
                         @AuthenticationPrincipal CustomUserDetail principal) {
        return boardService.update(id, requestDto, principal.getUserId());
    }

    @ApiOperation("게시글 삭제")
    @DeleteMapping("/board/{id}")
    public Object delete(@PathVariable Long id,
                         @AuthenticationPrincipal CustomUserDetail principal) {
        boardService.delete(id, principal.getUserId());
        return null;
    }

    @ApiOperation("게시글 목록")
    @GetMapping("/board")
    public Object list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "10") Integer size) {

        return boardService.list(PageRequest.of(page, size));
    }

    @ApiOperation("게시글 본문 보기")
    @GetMapping("/board/{id}")
    public Object view(@PathVariable Long id,
                       @AuthenticationPrincipal CustomUserDetail principal) {

        return boardService.view(id, principal.getUserId());
    }

}
