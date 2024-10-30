package com.multitap.auth.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindIdResponseVo {

    private String accountId;

    @Builder
    public FindIdResponseVo(String accountId) {
        this.accountId = accountId;
    }


}
