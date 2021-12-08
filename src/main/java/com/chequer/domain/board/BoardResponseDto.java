package com.chequer.domain.board;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel(description = "게시글 응답 DTO")
public class BoardResponseDto {

    @ApiModelProperty(value = "게시글 ID")
    private Long id;

    @ApiModelProperty(value = "제목")
    private String title;

    @ApiModelProperty(value = "내용")
    private String content;

    @ApiModelProperty(value = "작성자")
    private Long author;

    @ApiModelProperty(value = "조회수")
    private Integer hits;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.author = board.getAuthor();
        this.hits = board.getHits();
    }

}
