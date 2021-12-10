package com.chequer.exception;

import com.chequer.web.common.RestResponse;
import com.chequer.web.common.ResultType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BaseException.class)
    protected RestResponse baseExceptionHandle(BaseException e) {

        log.error("Base Exception : {}", e.getErrorCode().getDetail());

        return RestResponse.builder()
                .result(ResultType.FAIL)
                .error(e.getErrorCode())
                .build();

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected RestResponse notValidExceptionHandle(MethodArgumentNotValidException e) {

        log.error("Not Valid Exception : {}", e.getBindingResult());

        return RestResponse.builder()
                .result(ResultType.FAIL)
                .error(ErrorCode.E0001)
                .build();

    }
}
