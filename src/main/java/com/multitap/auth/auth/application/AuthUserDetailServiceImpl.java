package com.multitap.auth.auth.application;

import com.multitap.auth.auth.domain.AuthUserDetails;
import com.multitap.auth.auth.domain.Member;
import com.multitap.auth.auth.infrastructure.AuthRepository;
import com.multitap.auth.common.exception.BaseException;
import com.multitap.auth.common.response.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthUserDetailServiceImpl implements UserDetailsService {

    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authRepository.findByEmail(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));
    }

    public AuthUserDetails createUserDetails(Member member) {
        return AuthUserDetails.builder()
                .member(member)
                .build();

    }

}
