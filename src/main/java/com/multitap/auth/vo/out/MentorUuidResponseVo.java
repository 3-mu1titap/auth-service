package com.multitap.auth.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MentorUuidResponseVo {

    List<String> mentorUuid;

    @Builder
    public MentorUuidResponseVo(List<String> mentorUuid) {
        this.mentorUuid = mentorUuid;
    }

}
