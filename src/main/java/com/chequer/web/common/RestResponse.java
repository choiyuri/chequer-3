package com.chequer.web.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

/**
 * 모든 요청에 공통으로 사용되는 Response 객체
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class RestResponse {
    private ResultType result;
    private Object data;
    private Object error;

    @Builder
    public RestResponse(ResultType result, Object data, Object error) {
        this.result = result;
        this.data = data;
        this.error = error;
    }
}
