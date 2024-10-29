package com.multitap.auth.vo.in;

import com.multitap.auth.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SignUpRequestVo {

    @Schema(description = "이름", example = "강제리")
    private String name;

    @Schema(description = "닉네임", example = "제리누나")
    private String nickName;

    @Schema(description = "이메일", example = "test1234@gmail.com")
    private String email;

    @Schema(description = "아이디", example = "jerry0720")
    private String accountId;

    @Schema(description = "비밀번호", example = "jerry0720!")
    private String password;

    @Schema(description = "전화번호", example = "01012341234")
    private String phoneNumber;

    @Schema(description = "상태", example = "MENTEE")
    private Role role;

    @Builder
    public SignUpRequestVo(String name, String nickName, String email, String accountId, String password, String phoneNumber, Role role) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.accountId = accountId;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
}
