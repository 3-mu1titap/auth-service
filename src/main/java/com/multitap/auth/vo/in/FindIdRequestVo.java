package com.multitap.auth.vo.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class FindIdRequestVo {

    @Schema(description = "이메일", example = "test1234@gmail.com", nullable = true)
    private String email;
}
