package com.chequer.domain.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@ApiModel(description = "사용자 저장 요청 DTO")
public class MemberSaveRequestDto {

    @Builder
    public MemberSaveRequestDto(String firstName, String lastName, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
    }

    @NotEmpty
    @ApiModelProperty(value = "이름", example = "유리")
    private String firstName;

    @NotEmpty
    @ApiModelProperty(value = "성", example = "최")
    private String lastName;

    @NotEmpty
    @ApiModelProperty(value = "비밀번호", example = "1234")
    private String password;

    @Email
    @NotEmpty
    @ApiModelProperty(value = "이메일", example = "choiyuri9107@gmail.com")
    private String email;

    public Member toEntity() {
        return Member.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .role(Role.USER)
                .activated(Boolean.TRUE)
                .build();
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
