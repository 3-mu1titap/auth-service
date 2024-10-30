package com.multitap.auth.dto.in;

import com.multitap.auth.vo.in.UuidRequestVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UuidRequestDto {

    private String uuid;

    @Builder
    public UuidRequestDto(String uuid) {
        this.uuid = uuid;
    }

    public static UuidRequestDto from(UuidRequestVo uuidRequestVo) {
        return UuidRequestDto.builder()
                .uuid(uuidRequestVo.getUuid())
                .build();
    }
}
