package com.chequer.domain.member;

import com.chequer.domain.auth.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel(description = "사용자 응답 DTO")
public class MemberResponseDto {

    @ApiModelProperty(value = "사용자 ID")
    private Long id;

    @ApiModelProperty(value = "이름")
    private String firstName;

    @ApiModelProperty(value = "성")
    private String lastName;

    @ApiModelProperty(value = "이메일")
    private String email;

    @ApiModelProperty(value = "권한")
    private Role role;

    public MemberResponseDto(Member user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
