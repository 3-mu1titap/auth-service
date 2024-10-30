package com.multitap.auth.dto.in;

import com.multitap.auth.vo.in.FindIdRequestVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FindIdRequestDto {

    private String email;

    @Builder
    public FindIdRequestDto(String email) {
        this.email = email;
    }

    public static FindIdRequestDto from(FindIdRequestVo findIdRequestVo) {
        return FindIdRequestDto.builder()
                .email(findIdRequestVo.getEmail())
                .build();
    }




}


