package com.multitap.auth.provider;

import com.multitap.auth.application.OAuthUserDetailService;
import com.multitap.auth.entity.AuthUserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Primary;

@Slf4j
@RequiredArgsConstructor
@Component
@Primary
public class OAuthAuthenticationProvider implements AuthenticationProvider {

    private final OAuthUserDetailService authUserDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();
        log.info("email: {}", email);

        AuthUserDetail authUserDetail = (AuthUserDetail) authUserDetailService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(authUserDetail, null, authUserDetail.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}