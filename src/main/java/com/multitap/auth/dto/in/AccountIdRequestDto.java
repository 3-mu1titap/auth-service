package com.multitap.auth.dto.in;

import com.multitap.auth.vo.in.AccountIdRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountIdRequestDto {

    private String accountId;

    @Builder
    public AccountIdRequestDto(String accountId) {
        this.accountId = accountId;
    }

    public static AccountIdRequestDto from(AccountIdRequestVo accountIdRequestVo) {
        return AccountIdRequestDto.builder()
                .accountId(accountIdRequestVo.getAccountId())
                .build();
    }
}