package com.multitap.auth.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UuidResponseVo {

    private String uuid;

    @Builder
    public UuidResponseVo(String uuid) {
        this.uuid = uuid;
    }

}
