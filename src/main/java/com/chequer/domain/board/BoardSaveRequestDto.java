package com.chequer.domain.board;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(description = "게시글 저장 요청 DTO")
public class BoardSaveRequestDto {

    @Builder
    public BoardSaveRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @ApiModelProperty(value = "제목")
    private String title;

    @ApiModelProperty(value = "내용")
    private String content;

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .deleteYn(Boolean.FALSE)
                .build();
    }
}
