package com.multitap.kafka.producer;

import com.multitap.auth.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDto {

    private String uuid;
    private String name;
    private String nickName;
    private String email;
    private String accountId;
    private String phoneNumber;
    private String profileImage;

    @Builder
    public MemberDto(String uuid, String name, String nickName, String email, String accountId, String phoneNumber, String profileImage) {
        this.uuid = uuid;
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.accountId = accountId;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
    }

    public static MemberDto from(Member member) {
        return MemberDto.builder()
                .uuid(member.getUuid())
                .name(member.getName())
                .nickName(member.getNickName())
                .email(member.getEmail())
                .accountId(member.getAccountId())
                .phoneNumber(member.getPhoneNumber())
                .profileImage("https://adaptors-bucket.s3.ap-northeast-2.amazonaws.com/mentoring/1733832486599-thunderProfile.png")
                .build();
    }
}
