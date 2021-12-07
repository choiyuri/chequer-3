package com.chequer.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<ErrorResponse> baseExceptionHandle(BaseException e, WebRequest webRequest) {

        // TODO : logback-local.xml 설정 확인 필요.

        log.error("Base Exception : {}", e.getErrorCode().getDetail());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(e.getErrorCode().getDetail())
                .description(webRequest.getDescription(false))
                .build();

        return new ResponseEntity<>(errorResponse, e.getErrorCode().getHttpStatus());
    }
}
