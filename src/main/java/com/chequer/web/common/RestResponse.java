package com.chequer.web.common;

import com.chequer.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

/**
 * 모든 요청에 공통으로 사용되는 Response 객체
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse<T> {
    private ResultType result;
    private T data;
    private ErrorCode error;

    @Builder
    public RestResponse(ResultType result, T data, ErrorCode error) {
        this.result = result;
        this.data = data;
        this.error = error;
    }
}
