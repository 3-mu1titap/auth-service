package com.multitap.kafka.producer;

import com.multitap.auth.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NicknamePhoneDto {

    private String uuid;
    private String nickName;
    private String phoneNumber;

    @Builder
    public NicknamePhoneDto(String uuid, String nickName, String phoneNumber) {
        this.uuid = uuid;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
    }

    public static NicknamePhoneDto from(Member member) {
        return NicknamePhoneDto.builder()
                .uuid(member.getUuid())
                .nickName(member.getNickName())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }
}
