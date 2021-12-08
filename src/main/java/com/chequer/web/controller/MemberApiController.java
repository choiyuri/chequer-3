package com.chequer.web.controller;

import com.chequer.domain.member.MemberSaveRequestDto;
import com.chequer.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Api(tags = "사용자 API")
@RequestMapping("/api/v1")
public class MemberApiController {

    private final MemberService memberService;

    @ApiOperation("사용자 생성")
    @PostMapping("/signup")
    public Object save(@RequestBody MemberSaveRequestDto requestDto) {
        return memberService.save(requestDto);
    }
}
