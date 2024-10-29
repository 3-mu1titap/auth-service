package com.multitap.auth.dto.in;

import com.multitap.auth.entity.OAuth;
import com.multitap.auth.vo.in.OAuthSignInRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OAuthSignInRequestDto {

    private String provider;
    private String providerId;
    private String email;

    @Builder
    public OAuthSignInRequestDto(
            String provider,
            String providerId,
            String email
    ) {
        this.provider = provider;
        this.providerId = providerId;
        this.email = email;
    }

    public OAuth toEntity(String memberUuid) {
        return OAuth.builder()
                .provider(provider)
                .providerId(providerId)
                .memberUuid(memberUuid)
                .build();
    }

    public static OAuthSignInRequestDto from(OAuthSignInRequestVo oAuthSignInRequestVo) {
        return OAuthSignInRequestDto.builder()
                .provider(oAuthSignInRequestVo.getProvider())
                .providerId(oAuthSignInRequestVo.getProviderId())
                .email(oAuthSignInRequestVo.getProviderEmail())
                .build();
    }

}