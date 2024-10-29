package com.multitap.auth.dto.in;

import com.multitap.auth.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PasswordChangeRequestDto {

    private String password;

    @Builder
    public PasswordChangeRequestDto(String password) {
        this.password = password;
    }

    public Member toEntity(PasswordChangeRequestDto passwordChangeRequestDto, Member member) {
        return Member.builder()
                .id(member.getId())
                .uuid(member.getUuid())
                .name(member.getName())
                .nickName(member.getNickName())
                .email(member.getEmail())
                .accountId(member.getAccountId())
                .password(passwordChangeRequestDto.getPassword())
                .phoneNumber(member.getPhoneNumber())
                .role(member.getRole())
                .build();
    }
}