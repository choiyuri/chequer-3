package com.chequer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Valid
    E0001(HttpStatus.BAD_REQUEST, "입력 값이 유효하지 않습니다."),

    // 사용자
    E1001(HttpStatus.BAD_REQUEST,"이미 등록되어 있는 이메일입니다."),
    E1002(HttpStatus.BAD_REQUEST,"해당 사용자가 존재하지 않습니다."),
    E1003(HttpStatus.FORBIDDEN,"해당 사용자는 권한이 없습니다."),
    E1004(HttpStatus.BAD_REQUEST,"계정이 활성화 되어 있지 않습니다."),

    // 게시글
    E2001(HttpStatus.OK,"해당 게시글이 존재하지 않습니다."),

    // JWT
    E3001(HttpStatus.UNAUTHORIZED,"잘못된 JWT 서명입니다."),
    E3002(HttpStatus.UNAUTHORIZED,"만료된 JWT 토큰입니다."),
    E3003(HttpStatus.UNAUTHORIZED,"지원되지 않는 JWT 토큰입니다."),
    E3004(HttpStatus.UNAUTHORIZED,"JWT 토큰이 잘못되었습니다."),
    E3005(HttpStatus.UNAUTHORIZED,"인증에 실패하였습니다."),

    ;

    private final HttpStatus status;
    private final String detail;
}
