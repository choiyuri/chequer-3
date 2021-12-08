package com.chequer.exception;

import com.chequer.web.RestResponse;
import com.chequer.web.ResultType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BaseException.class)
    protected RestResponse baseExceptionHandle(BaseException e, WebRequest webRequest) {

        log.error("Base Exception : {}", e.getErrorCode().getDetail());

        return RestResponse.builder()
                .result(ResultType.FAIL)
                .error(e.getErrorCode())
                .build();

    }
}
