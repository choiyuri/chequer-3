package com.chequer.domain.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@ApiModel(description = "로그인 요청 DTO")
public class LoginDto {

    @Email
    @NotNull
    @ApiModelProperty(value = "이메일", example = "choiyuri9107@gmail.com")
    private String email;

    @NotNull
    @ApiModelProperty(value = "비밀번호", example = "1234")
    private String password;
}