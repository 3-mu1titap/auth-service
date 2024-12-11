package com.multitap.auth.entity;

import com.multitap.auth.common.response.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String uuid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String accountId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public Member(Long id, String uuid, String name, String nickName, String email, String accountId, String password, String phoneNumber, Role role) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.accountId = accountId;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

}
