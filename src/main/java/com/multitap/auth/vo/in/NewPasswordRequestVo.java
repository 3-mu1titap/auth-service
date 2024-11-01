package com.multitap.auth.vo.in;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NewPasswordRequestVo {

    private String password;
    private String newPassword;

    @Builder
    public NewPasswordRequestVo(String password, String newPassword) {
        this.password = password;
        this.newPassword = newPassword;
    }

}
