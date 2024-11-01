package com.multitap.auth.vo.out;

import com.multitap.auth.entity.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInResponseVo {

    private String accessToken;
    private String refreshToken;
    private String uuid;
    private Role role;

    @Builder
    public SignInResponseVo(String accessToken, String refreshToken, String uuid, Role role) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.uuid = uuid;
        this.role = role;
    }

}
