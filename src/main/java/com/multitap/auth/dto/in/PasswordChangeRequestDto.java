package com.multitap.auth.dto.in;

import com.multitap.auth.entity.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class PasswordChangeRequestDto {

    private String password;
    private String uuid;

    @Builder
    public PasswordChangeRequestDto(String password, String uuid) {

        this.password = password;
        this.uuid = uuid;
    }

    // 비밀번호 변경
    public Member toEntity(PasswordChangeRequestDto passwordChangeRequestDto, Member member, PasswordEncoder passwordEncoder) {
        return Member.builder()
                .id(member.getId())
                .uuid(member.getUuid())
                .name(member.getName())
                .nickName(member.getNickName())
                .email(member.getEmail())
                .accountId(member.getAccountId())
                .password(passwordEncoder.encode(passwordChangeRequestDto.getPassword()))
                .phoneNumber(member.getPhoneNumber())
                .role(member.getRole())
                .build();
    }
}