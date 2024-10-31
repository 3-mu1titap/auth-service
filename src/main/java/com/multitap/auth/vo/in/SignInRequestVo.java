package com.multitap.auth.vo.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInRequestVo {

    @Schema(description = "아이디", example = "jerry0720")
    private String accountId;

    @Schema(description = "비밀번호", example = "jerry0720!")
    private String password;
}
