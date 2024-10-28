package com.multitap.auth.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInResponseVo {

    private String accessToken;
    private String refreshToken;
    private String uuid;

    @Builder
    public SignInResponseVo(String accessToken, String refreshToken, String uuid) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.uuid = uuid;
    }

}
