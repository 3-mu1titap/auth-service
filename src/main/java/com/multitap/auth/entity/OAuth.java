package com.multitap.auth.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class OAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String memberUuid;
    @Column(nullable = false, length = 50)
    private String provider;
    @Column(nullable = false, length = 50)
    private Long providerId;

    @Builder
    public OAuth(
            String memberUuid,
            String provider,
            Long providerId
    ) {
        this.memberUuid = memberUuid;
        this.provider = provider;
        this.providerId = providerId;
    }

}