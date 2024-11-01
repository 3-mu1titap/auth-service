package com.multitap.auth.dto.out;

import com.multitap.auth.entity.Member;
import com.multitap.auth.vo.out.UuidResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UuidResponseDto {

    private String uuid;

    @Builder
    public UuidResponseDto(String uuid) {
        this.uuid = uuid;
    }

    public static UuidResponseDto from(Member member) {
        return UuidResponseDto.builder()
                .uuid(member.getUuid())
                .build();
    }

    public UuidResponseVo toVo() {
        return UuidResponseVo.builder()
                .uuid(uuid)
                .build();
    }

}
