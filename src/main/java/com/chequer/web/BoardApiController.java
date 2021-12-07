package com.chequer.web;

import com.chequer.domain.board.BoardResponseDto;
import com.chequer.domain.board.BoardSaveRequestDto;
import com.chequer.domain.board.BoardUpdateRequestDto;
import com.chequer.service.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public Long save(@RequestBody BoardSaveRequestDto requestDto) {
        return boardService.save(requestDto);
    }

    @ApiOperation("게시글 수정")
    @PutMapping("/board/{id}")
    public Long update(@PathVariable Long id,
                       @RequestBody BoardUpdateRequestDto requestDto,
                       Principal principal) {
        return boardService.update(id, requestDto, principal.getName());
    }

    @ApiOperation("게시글 삭제")
    @DeleteMapping("/board/{id}")
    public Long delete(@PathVariable Long id,
                       Principal principal) {
        return boardService.delete(id, principal.getName());
    }

    @ApiOperation("게시글 목록")
    @GetMapping("/board")
    public Page<BoardResponseDto> list(@RequestParam(defaultValue = "0") Integer page,
                                       @RequestParam(defaultValue = "10") Integer size) {

        return boardService.list(PageRequest.of(page, size));
    }

    @ApiOperation("게시글 본문 보기")
    @GetMapping("/board/{id}")
    public BoardResponseDto view(@PathVariable Long id,
                                 Principal principal) {

        return boardService.view(id, principal.getName());
    }

}
