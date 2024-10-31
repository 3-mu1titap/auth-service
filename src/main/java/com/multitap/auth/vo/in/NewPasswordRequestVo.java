package com.multitap.auth.vo.in;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NewPasswordRequestVo {

    private String newPassword;

    @Builder
    public NewPasswordRequestVo(String newPassword) {
        this.newPassword = newPassword;
    }

}
