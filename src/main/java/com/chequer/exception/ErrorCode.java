package com.chequer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시글이 존재하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 사용자가 존재하지 않습니다."),
    USER_FORBIDDEN(HttpStatus.FORBIDDEN, "해당 사용자는 권한이 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
