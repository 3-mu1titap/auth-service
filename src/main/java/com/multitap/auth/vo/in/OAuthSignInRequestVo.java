package com.multitap.auth.vo.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class OAuthSignInRequestVo {

    @Schema(description = "제공자", example = "kakao", nullable = true)
    private String provider;

    @Schema(description = "제공자id", example = "kakao", nullable = true)
    private String providerId;

    @Schema(description = "소셜 이메일", example = "test1234@gamil.com", nullable = true)
    private String providerEmail;

}