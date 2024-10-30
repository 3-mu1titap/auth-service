package com.multitap.auth.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Slf4j
@Getter
@NoArgsConstructor
public class AuthUserDetail implements UserDetails {

    private String email;
    private String accountId;
    private String password;
    private Role role;
    private String uuid;

    @Builder
    public AuthUserDetail(Member member) {
        this.email = member.getEmail();
        this.accountId = member.getAccountId();
        this.password = member.getPassword();
        this.role = member.getRole();
        this.uuid = member.getUuid();

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name())); // 자신의 역할 반환
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
