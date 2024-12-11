package com.multitap.auth.dto.out;

import com.multitap.auth.vo.out.MentorUuidResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MentorUuidResponseDto {

    List<String> mentorUuid;

    @Builder
    public MentorUuidResponseDto(List<String> mentorUuid) {
        this.mentorUuid = mentorUuid;
    }

    public static MentorUuidResponseDto from(List<String> mentorUuid){
        return MentorUuidResponseDto.builder()
                .mentorUuid(mentorUuid)
                .build();
    }

    public MentorUuidResponseVo toVo(MentorUuidResponseDto mentorUuidResponseDto){
        return MentorUuidResponseVo.builder()
                .mentorUuid(mentorUuidResponseDto.mentorUuid)
                .build();
    }
}
