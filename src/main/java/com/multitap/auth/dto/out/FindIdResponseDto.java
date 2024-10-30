package com.multitap.auth.dto.out;

import com.multitap.auth.entity.Member;
import com.multitap.auth.vo.out.FindIdResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindIdResponseDto {

    private String accountId;

    @Builder
    public FindIdResponseDto(String accountId) {
        this.accountId = accountId;
    }

    public static FindIdResponseDto from(Member member){
        return FindIdResponseDto.builder()
                .accountId(member.getAccountId())
                .build();
    }

    public FindIdResponseVo toVo() {
        return FindIdResponseVo.builder()
                .accountId(accountId)
                .build();
    }

}
