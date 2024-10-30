package com.multitap.auth.dto.in;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CurrentPasswordRequestDto {

    private String password;
    private String uuid;

    @Builder
    public CurrentPasswordRequestDto(String password, String uuid) {

        this.password = password;
        this.uuid = uuid;
    }

}