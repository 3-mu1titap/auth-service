package com.multitap.auth.dto.in;

import com.multitap.auth.entity.Member;
import com.multitap.auth.vo.in.FindPasswordRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
public class FindPasswordRequestDto {
    private String accountId;

    @Builder
    public FindPasswordRequestDto(String accountId) {
        this.accountId = accountId;
    }

    public static FindPasswordRequestDto from(FindPasswordRequestVo findPasswordRequestVo) {
        return FindPasswordRequestDto.builder()
                .accountId(findPasswordRequestVo.getAccountId())
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
