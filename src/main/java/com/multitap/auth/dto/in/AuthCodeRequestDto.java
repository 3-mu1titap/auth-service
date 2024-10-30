package com.multitap.auth.dto.in;

import com.multitap.auth.vo.in.AuthCodeRequestVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthCodeRequestDto {
    public String authCode;

    @Builder
    public AuthCodeRequestDto(String authCode) {
        this.authCode = authCode;
    }

    public static AuthCodeRequestDto from(AuthCodeRequestVo authCodeRequestVo) {
        return AuthCodeRequestDto.builder()
                .authCode(authCodeRequestVo.getAuthCode())
                .build();
    }

}
