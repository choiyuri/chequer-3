package com.chequer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    E0001("해당 게시글이 존재하지 않습니다."),
    E0002("해당 사용자가 존재하지 않습니다."),
    E0003("해당 사용자는 권한이 없습니다."),
    ;

    private final String detail;
}
