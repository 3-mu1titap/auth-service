package com.multitap.auth.vo.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PasswordResetRequestVo {

    @Schema(description = "아이디", example = "jerry0720", nullable = true)
    private String accountId;

}
