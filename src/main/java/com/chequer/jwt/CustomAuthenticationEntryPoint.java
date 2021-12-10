package com.chequer.jwt;

import com.chequer.exception.ErrorCode;
import com.chequer.web.RestResponse;
import com.chequer.web.ResultType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ErrorCode exception = (ErrorCode) request.getAttribute("exception");

        if (Objects.isNull(exception)) {
            setResponse(response, ErrorCode.E3005);
        } else {
            setResponse(response, exception);
        }
    }

    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        RestResponse restResponse = RestResponse.builder()
                .result(ResultType.FAIL)
                .error(errorCode)
                .build();

        response.getWriter().print(objectMapper.writeValueAsString(restResponse));
    }

}
