package com.multitap.auth.dto.in;


import com.multitap.auth.entity.Member;
import com.multitap.auth.entity.Role;
import com.multitap.auth.vo.in.SignUpRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Getter
@NoArgsConstructor
@ToString
public class SignUpRequestDto {

    private String name;
    private String nickName;
    private String email;
    private String password;
    private String phoneNumber;
    private Role role;

    @Builder
    public SignUpRequestDto(String name, String nickName, String email, String password, String phoneNumber, Role role) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .uuid(UUID.randomUUID().toString())
                .name(name)
                .nickName(nickName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .phoneNumber(phoneNumber)
                .role(role)
                .build();
    }

    public static SignUpRequestDto from(SignUpRequestVo signUpRequestVo) {
        return SignUpRequestDto.builder()
                .name(signUpRequestVo.getName())
                .nickName(signUpRequestVo.getNickName())
                .email(signUpRequestVo.getEmail())
                .password(signUpRequestVo.getPassword())
                .phoneNumber(signUpRequestVo.getPhoneNumber())
                .role(signUpRequestVo.getRole())
                .build();
    }

}
