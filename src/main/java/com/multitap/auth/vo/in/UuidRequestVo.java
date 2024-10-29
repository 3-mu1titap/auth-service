package com.multitap.auth.vo.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UuidRequestVo {
    @Schema(description = "회원 UUID", example = "")
    private String uuid;

}
