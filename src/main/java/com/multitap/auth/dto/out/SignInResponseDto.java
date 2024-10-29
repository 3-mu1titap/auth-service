package com.multitap.auth.dto.out;

import com.multitap.auth.entity.Member;
import com.multitap.auth.vo.out.SignInResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInResponseDto {

    private String accessToken;
    private String refreshToken;
    private String uuid;

    @Builder
    public SignInResponseDto(String accessToken, String refreshToken, String uuid) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.uuid = uuid;
    }

    public SignInResponseVo toVo() {
        return SignInResponseVo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .uuid(uuid)
                .build();
    }

    public static SignInResponseDto of(Member member, String accessToken, String refreshToken) {
        return SignInResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .uuid(member.getUuid())
                .build();
    }

}
