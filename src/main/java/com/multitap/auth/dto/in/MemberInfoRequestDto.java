package com.multitap.auth.dto.in;

import com.multitap.auth.entity.Member;
import com.multitap.auth.vo.in.MemberInfoRequestVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberInfoRequestDto {

    private String uuid;
    private String nickname;
    private String phoneNumber;

    @Builder
    public MemberInfoRequestDto(String uuid, String nickname, String phoneNumber) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }

    public static MemberInfoRequestDto from(MemberInfoRequestVo memberInfoRequestVo, String uuid) {
        return MemberInfoRequestDto.builder()
                .uuid(uuid)
                .nickname(memberInfoRequestVo.getNickName())
                .phoneNumber(memberInfoRequestVo.getPhoneNumber())
                .build();

    }

    public Member toEntity(Member member) {
        return Member.builder()
                .id(member.getId())
                .uuid(uuid)
                .name(member.getName())
                .nickName(nickname)
                .email(member.getEmail())
                .accountId(member.getAccountId())
                .password(member.getPassword())
                .phoneNumber(phoneNumber)
                .role(member.getRole())
                .build();
    }

}
