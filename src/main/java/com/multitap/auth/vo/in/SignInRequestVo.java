package com.multitap.auth.vo.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SignInRequestVo {

    @Schema(description = "이메일", example = "test1234@gmail.com")
    private String email;

    @Schema(description = "비밀번호", example = "!test1234")
    private String password;
}
