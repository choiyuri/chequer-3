package com.chequer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Valid
    E0001("입력 값이 유효하지 않습니다."),

    // 사용자
    E1001("이미 등록되어 있는 이메일입니다."),
    E1002("해당 사용자가 존재하지 않습니다."),
    E1003("해당 사용자는 권한이 없습니다."),
    E1004("계정이 활성화 되어 있지 않습니다."),

    // 게시글
    E2001("해당 게시글이 존재하지 않습니다."),

    // JWT
    E3001("잘못된 JWT 서명입니다."),
    E3002("만료된 JWT 토큰입니다."),
    E3003("지원되지 않는 JWT 토큰입니다."),
    E3004("JWT 토큰이 잘못되었습니다."),
    E3005("인증에 실패하였습니다."),

    ;

    private final String detail;
}
