package com.multitap.auth.dto.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefreshTokenRequestDto {

    private String refreshToken;

    @Builder
    public RefreshTokenRequestDto(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public static RefreshTokenRequestDto from(String token) {
        return RefreshTokenRequestDto.builder()
                .refreshToken(token)
                .build();
    }
}
