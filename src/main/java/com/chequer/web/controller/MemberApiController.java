package com.chequer.web.controller;

import com.chequer.domain.member.MemberSaveRequestDto;
import com.chequer.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Api(tags = "사용자 API")
@RequestMapping("/api/v1")
public class MemberApiController {

    private final MemberService memberService;

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("사용자 생성")
    @PostMapping("/signup")
    public Object save(@RequestBody MemberSaveRequestDto requestDto) {
        return memberService.save(requestDto);
    }
}
