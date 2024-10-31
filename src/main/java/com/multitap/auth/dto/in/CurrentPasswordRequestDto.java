package com.multitap.auth.dto.in;

import com.multitap.auth.vo.in.CurrentPasswordRequestVo;
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


    public static CurrentPasswordRequestDto from(CurrentPasswordRequestVo currentPasswordRequestVo,String uuid) {
        return CurrentPasswordRequestDto.builder()
                .password(currentPasswordRequestVo.getPassword())
                .uuid(uuid)
                .build();
    }

}