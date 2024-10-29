package com.multitap.auth.dto.in;

import com.multitap.auth.entity.Member;
import com.multitap.auth.vo.in.PasswordResetRequestVo;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class PasswordResetRequestDto {
    private String accountId;

    @Builder
    public PasswordResetRequestDto(String accountId) {
        this.accountId = accountId;
    }

    public static PasswordResetRequestDto from(PasswordResetRequestVo passwordResetRequestVo) {
        return PasswordResetRequestDto.builder()
                .accountId(passwordResetRequestVo.getAccountId())
                .build();
    }

    // 임시 비밀번호로 비밀번호 변경
    public Member changeTemporaryPassword(PasswordEncoder passwordEncoder, Member member, String temporaryPassword) {
        return Member.builder()
                .id(member.getId())
                .uuid(member.getUuid())
                .name(member.getName())
                .nickName(member.getNickName())
                .email(member.getEmail())
                .accountId(member.getAccountId())
                .password(passwordEncoder.encode(temporaryPassword))
                .phoneNumber(member.getPhoneNumber())
                .role(member.getRole())
                .build();
    }

}
