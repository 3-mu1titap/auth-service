package com.multitap.auth.dto.in;

import com.multitap.auth.vo.in.SignInRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInRequestDto {

    private String accountId;
    private String password;

    @Builder
    public SignInRequestDto(String accountId, String password) {
        this.accountId = accountId;
        this.password = password;
    }

    public static SignInRequestDto from(SignInRequestVo signInRequestVo) {
        return SignInRequestDto.builder()
                .accountId(signInRequestVo.getAccountId())
                .password(signInRequestVo.getPassword())
                .build();
    }

}
