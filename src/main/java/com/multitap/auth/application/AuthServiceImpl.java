package com.multitap.auth.application;

import com.multitap.auth.dto.in.OAuthSignInRequestDto;
import com.multitap.auth.dto.in.SignInRequestDto;
import com.multitap.auth.dto.in.SignUpRequestDto;
import com.multitap.auth.dto.out.SignInResponseDto;
import com.multitap.auth.entity.AuthUserDetail;
import com.multitap.auth.entity.Member;
import com.multitap.auth.infrastructure.MemberRepository;
import com.multitap.auth.infrastructure.OAuthRepository;
import com.multitap.auth.common.exception.BaseException;
import com.multitap.auth.common.jwt.JwtTokenProvider;
import com.multitap.auth.common.response.BaseResponseStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final OAuthRepository oAuthRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(SignUpRequestDto signUpRequestDto) {

        try {
            memberRepository.save(signUpRequestDto.toEntity(passwordEncoder));
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_RESTORE);
        }

    }

    @Override
    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {

        Member member = memberRepository.findByEmail(signInRequestDto.getEmail()).orElseThrow(
                () -> new BaseException(BaseResponseStatus.FAILED_TO_LOGIN)
        );

        try {
            return createToken(authenticate(member, signInRequestDto.getPassword()));

        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
        }

    }

    @Override
    public SignInResponseDto oAuthSignIn(OAuthSignInRequestDto oAuthSignInRequestDto) {

        Member member = memberRepository.findByEmail(oAuthSignInRequestDto.getEmail()).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NO_EXIST_USER)
        );

        oAuthRepository.findByMemberUuid(member.getUuid()).orElseGet(
                () -> oAuthRepository.save(oAuthSignInRequestDto.toEntity(member.getUuid()))
        );

        return createToken(oAuthAuthenticate(member));

    }

    private SignInResponseDto createToken(Authentication authentication) {
        AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal();
        return jwtTokenProvider.generateToken(authUserDetail);
    }

    private Authentication authenticate(Member member, String inputPassword) {
        AuthUserDetail authUserDetail = new AuthUserDetail(member);
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authUserDetail.getUsername(),
                        inputPassword
                )
        );
    }

    private Authentication oAuthAuthenticate(Member member) {
        AuthUserDetail authUserDetail = new AuthUserDetail(member);
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authUserDetail.getEmail(),
                        null
                )
        );
    }

}