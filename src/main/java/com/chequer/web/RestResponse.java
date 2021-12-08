package com.chequer.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

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
