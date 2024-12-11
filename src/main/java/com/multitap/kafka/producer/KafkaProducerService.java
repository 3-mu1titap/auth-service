package com.multitap.kafka.producer;

public interface KafkaProducerService {

    void sendCreateMember(MemberDto memberDto);
    void sendCreateMemberInfo(NicknamePhoneDto nicknamePhoneDto);
    void sendMemberUuid(MemberUuidDataDto MemberUuidDto);
}
