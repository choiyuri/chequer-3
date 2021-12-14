package com.chequer.exception;

import com.chequer.web.common.RestResponse;
import com.chequer.web.common.ResultType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.constraints.Null;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<RestResponse<Nullable>> baseExceptionHandle(BaseException e) {

        log.error("Base Exception : {}", (Object[]) e.getStackTrace());

        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(RestResponse.<Nullable>builder()
                        .result(ResultType.FAIL)
                        .error(e.getErrorCode())
                        .build());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<RestResponse<Nullable>> notValidExceptionHandle(MethodArgumentNotValidException e) {

        log.error("Not Valid Exception : {}", e.getBindingResult());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(RestResponse.<Nullable>builder()
                        .result(ResultType.FAIL)
                        .error(ErrorCode.E0001)
                        .build());

    }
}
