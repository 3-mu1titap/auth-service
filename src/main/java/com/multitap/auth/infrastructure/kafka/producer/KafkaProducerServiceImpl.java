package com.multitap.auth.infrastructure.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendCreateMember(MemberDto memberDto) {
        try {
            kafkaTemplate.send("create-member-topic", memberDto);
        } catch (Exception e) {
            log.info("create member event send 실패 : " + e);
            throw new RuntimeException(e);
        }
    }
}
