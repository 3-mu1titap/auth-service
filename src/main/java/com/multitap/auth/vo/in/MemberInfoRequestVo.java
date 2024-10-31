package com.multitap.auth.vo.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class MemberInfoRequestVo {

    @Schema(description = "닉네임", example = "제리누나", nullable = true)
    private String nickName;

    @Schema(description = "전화번호", example = "01012341234", nullable = true)
    private String phoneNumber;


}
