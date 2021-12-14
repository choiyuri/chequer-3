package com.chequer.web.controller;

import com.chequer.domain.auth.CustomUserDetail;
import com.chequer.domain.board.BoardResponseDto;
import com.chequer.domain.board.BoardSaveRequestDto;
import com.chequer.domain.board.BoardUpdateRequestDto;
import com.chequer.service.BoardService;
import com.chequer.web.common.PageResponse;
import com.chequer.web.common.RestResponse;
import com.chequer.web.common.ResultType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
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
    public RestResponse<BoardResponseDto> save(@Valid @RequestBody BoardSaveRequestDto requestDto) {

        return RestResponse.<BoardResponseDto>builder()
                .data(new BoardResponseDto(boardService.save(requestDto)))
                .result(ResultType.SUCCESS)
                .build();
    }

    @ApiOperation("게시글 수정")
    @PutMapping("/board/{id}")
    public RestResponse<BoardResponseDto> update(@PathVariable Long id,
                                                 @Valid @RequestBody BoardUpdateRequestDto requestDto,
                                                 @AuthenticationPrincipal CustomUserDetail principal) {

        return RestResponse.<BoardResponseDto>builder()
                .data(new BoardResponseDto(boardService.update(id, requestDto, principal.getUserId())))
                .result(ResultType.SUCCESS)
                .build();
    }

    @ApiOperation("게시글 삭제")
    @DeleteMapping("/board/{id}")
    public RestResponse<Nullable> delete(@PathVariable Long id,
                                         @AuthenticationPrincipal CustomUserDetail principal) {

        boardService.delete(id, principal.getUserId());
        return RestResponse.<Nullable>builder()
                .result(ResultType.SUCCESS)
                .build();
    }

    @ApiOperation("게시글 목록")
    @GetMapping("/board")
    public RestResponse<PageResponse<BoardResponseDto>> list(@RequestParam(defaultValue = "0") Integer page,
                                                             @RequestParam(defaultValue = "10") Integer size) {

        PageResponse<BoardResponseDto> pageResponse = PageResponse.<BoardResponseDto>builder()
                .page(boardService.list(PageRequest.of(page, size)).map(BoardResponseDto::new))
                .build();

        return RestResponse.<PageResponse<BoardResponseDto>>builder()
                .data(pageResponse)
                .result(ResultType.SUCCESS)
                .build();
    }

    @ApiOperation("게시글 본문 보기")
    @GetMapping("/board/{id}")
    public RestResponse<BoardResponseDto> view(@PathVariable Long id,
                                               @AuthenticationPrincipal CustomUserDetail principal) {

        return RestResponse.<BoardResponseDto>builder()
                .data(new BoardResponseDto(boardService.view(id, principal.getUserId())))
                .result(ResultType.SUCCESS)
                .build();
    }

}
