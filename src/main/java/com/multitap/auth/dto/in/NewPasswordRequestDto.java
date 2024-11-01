package com.multitap.auth.dto.in;

import com.multitap.auth.entity.Member;
import com.multitap.auth.vo.in.NewPasswordRequestVo;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class NewPasswordRequestDto {

    private String uuid;
    private String password;
    private String newPassword;

    @Builder
    public NewPasswordRequestDto(String uuid, String password, String newPassword) {
        this.uuid = uuid;
        this.password = password;
        this.newPassword = newPassword;
    }

    public static NewPasswordRequestDto from(NewPasswordRequestVo newPasswordRequestVo, String uuid) {
        return NewPasswordRequestDto.builder()
                .uuid(uuid)
                .password(newPasswordRequestVo.getPassword())
                .newPassword(newPasswordRequestVo.getNewPassword())
                .build();
    }

    // 비밀번호 변경
    public Member toEntity(NewPasswordRequestDto newPasswordRequestDto, Member member, PasswordEncoder passwordEncoder) {
        return Member.builder()
                .id(member.getId())
                .uuid(member.getUuid())
                .name(member.getName())
                .nickName(member.getNickName())
                .email(member.getEmail())
                .accountId(member.getAccountId())
                .password(passwordEncoder.encode(newPasswordRequestDto.getNewPassword()))
                .phoneNumber(member.getPhoneNumber())
                .role(member.getRole())
                .build();
    }
}
