package com.multitap.auth.dto.out;

import com.multitap.auth.vo.out.RefreshTokenResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefreshTokenResponseDto {

    private String accessToken;

    @Builder
    public RefreshTokenResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public RefreshTokenResponseVo toVo(){
        return RefreshTokenResponseVo.builder()
               .accessToken(accessToken)
               .build();
    }

}
