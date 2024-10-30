package com.multitap.auth.vo.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class AuthCodeRequestVo {

    @Schema(description = "인증코드", example = "", nullable = true)
    private String authCode;

}


