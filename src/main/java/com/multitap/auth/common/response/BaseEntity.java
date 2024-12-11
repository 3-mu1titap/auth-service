package com.multitap.auth.common.response;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Column(updatable = false)
    private LocalDateTime createdAt; // 최초 생성일

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime updatedAt; // 마지막 수정일

    @PrePersist // 저장 전에 동작
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate // 업데이트 전에 동작
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
