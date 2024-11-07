package com.multitap.auth.infrastructure.kafka.producer;

public interface KafkaProducerService {

    void sendCreateMember(MemberDto memberDto);


}
