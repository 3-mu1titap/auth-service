package com.multitap.auth.vo.in;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NewPasswordRequestVo {

    private String newPassword;

    @Builder
    public NewPasswordRequestVo(String newPassword) {
        this.newPassword = newPassword;
    }

}
