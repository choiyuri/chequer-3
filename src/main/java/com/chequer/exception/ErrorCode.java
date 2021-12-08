package com.chequer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 사용자,
    E1001("이미 등록되어 있는 이메일입니다."),
    E1002("해당 사용자가 존재하지 않습니다."),
    E1003("해당 사용자는 권한이 없습니다."),

    // 게시글
    E2001("해당 게시글이 존재하지 않습니다."),
    ;

    private final String detail;
}
