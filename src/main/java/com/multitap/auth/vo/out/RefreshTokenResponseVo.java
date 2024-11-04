package com.multitap.auth.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefreshTokenResponseVo {

    private String accessToken;

    @Builder
    public RefreshTokenResponseVo(String accessToken) {
        this.accessToken = accessToken;
    }
}
