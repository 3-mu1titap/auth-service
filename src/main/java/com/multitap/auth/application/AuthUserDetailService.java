package com.multitap.auth.application;

import com.multitap.auth.entity.AuthUserDetail;
import com.multitap.auth.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {
        return new AuthUserDetail(memberRepository.findByAccountId(accountId).orElseThrow(() -> new UsernameNotFoundException(accountId)));
    }
}
