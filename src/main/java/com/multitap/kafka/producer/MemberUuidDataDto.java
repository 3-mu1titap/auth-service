package com.multitap.kafka.producer;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberUuidDataDto {

    List<String> menteeUuid;
    List<String> mentorUuid;

    @Builder
    public MemberUuidDataDto(List<String> menteeUuid, List<String> mentorUuid) {
        this.menteeUuid = menteeUuid;
        this.mentorUuid = mentorUuid;
    }

}