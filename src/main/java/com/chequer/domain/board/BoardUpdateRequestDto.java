package com.chequer.domain.board;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(description = "게시글 수정 요청 DTO")
public class BoardUpdateRequestDto {

    @Builder
    public BoardUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @ApiModelProperty(value = "제목")
    private String title;

    @ApiModelProperty(value = "내용")
    private String content;
}
