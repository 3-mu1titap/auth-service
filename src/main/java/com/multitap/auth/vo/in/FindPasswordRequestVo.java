package com.multitap.auth.vo.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindPasswordRequestVo {

    @Schema(description = "아이디", example = "jerry0720", nullable = true)
    private String accountId;

}
