package com.multitap.auth.vo.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CurrentPasswordRequestVo {

    @Schema(description = "기존 비밀번호", example = "jerry0720!", nullable = true)
    private String password;
}
