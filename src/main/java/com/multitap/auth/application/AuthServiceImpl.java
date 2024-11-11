package com.multitap.auth.application;

import com.multitap.auth.dto.in.*;
import com.multitap.auth.dto.out.RefreshTokenResponseDto;
import com.multitap.auth.dto.out.SignInResponseDto;
import com.multitap.auth.dto.out.UuidResponseDto;
import com.multitap.auth.entity.AuthUserDetail;
import com.multitap.auth.entity.Member;
import com.multitap.auth.entity.OAuth;
import com.multitap.auth.infrastructure.MemberRepository;
import com.multitap.auth.infrastructure.OAuthRepository;
import com.multitap.auth.common.exception.BaseException;
import com.multitap.auth.common.jwt.JwtTokenProvider;
import com.multitap.auth.common.response.BaseResponseStatus;
import com.multitap.auth.infrastructure.kafka.producer.KafkaProducerService;
import com.multitap.auth.infrastructure.kafka.producer.MemberDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
//    private final KafkaProducerService kafkaProducerService;

    @Override
    public UuidResponseDto signUp(SignUpRequestDto signUpRequestDto) {

        if (memberRepository.findByEmail(signUpRequestDto.getEmail()).isPresent()) {
            throw new BaseException(BaseResponseStatus.DUPLICATED_USER);
        }
        Member member = memberRepository.save(signUpRequestDto.toEntity(passwordEncoder));
//        kafkaProducerService.sendCreateMember(MemberDto.from(member));
        return UuidResponseDto.from(member);
    }

    @Override
    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {

        Member member = memberRepository.findByAccountId(signInRequestDto.getAccountId()).orElseThrow(
                () -> new BaseException(BaseResponseStatus.FAILED_TO_LOGIN));

        if (!new BCryptPasswordEncoder().matches(signInRequestDto.getPassword(), member.getPassword())) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
        }

        return createToken(authenticate(member, signInRequestDto.getPassword()));
    }

    @Override
    public void checkAccountId(AccountIdRequestDto accountIdRequestDto) {
        if (memberRepository.findByAccountId(accountIdRequestDto.getAccountId()).isPresent()) {
            throw new BaseException(BaseResponseStatus.DUPLICATED_ACCOUNT_ID);
        }
    }

    @Override
    public SignInResponseDto oAuthSignIn(OAuthSignInRequestDto oAuthSignInRequestDto) {

        Member member = memberRepository.findByEmail(oAuthSignInRequestDto.getEmail()).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NO_EXIST_USER) // 회원가입 페이지로 이동
        );

        // 소셜 DB에서 providerId를 사용하여 OAuth 정보 조회
        Optional<OAuth> oAuth = oAuthRepository.findByMemberUuid(member.getUuid());

        // providerId가 없는 경우, 새 OAuth 엔티티 생성 및 저장
        if (oAuth.isEmpty()) {
            oAuthRepository.save(oAuthSignInRequestDto.toEntity(member.getUuid()));
        }

        // 회원이 존재하므로 로그인 처리를 진행
        return createToken(oAuthAuthenticate(member));
    }

    // 비밀번호 변경
    @Override
    public void changePassword(NewPasswordRequestDto newPasswordRequestDto) {

        Member member = memberRepository.findByUuid(newPasswordRequestDto.getUuid()).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NO_EXIST_USER)
        );

        if (!new BCryptPasswordEncoder().matches(newPasswordRequestDto.getPassword(), member.getPassword())) {
            throw new BaseException(BaseResponseStatus.PASSWORD_MATCH_FAILED);
        }

        memberRepository.save(newPasswordRequestDto.toEntity(newPasswordRequestDto, member, passwordEncoder));
    }

    @Override
    public void changeMemberInfo(MemberInfoRequestDto memberInfoRequestDto) {
        Member member = memberRepository.findByUuid(memberInfoRequestDto.getUuid()).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NO_EXIST_USER)
        );
        memberRepository.save(memberInfoRequestDto.toEntity(member));
    }

    @Override
    public RefreshTokenResponseDto refreshAccess(RefreshTokenRequestDto refreshTokenRequestDto) {
        return jwtTokenProvider.generateAccessTokenFromRefreshToken(refreshTokenRequestDto);
    }


    private SignInResponseDto createToken(Authentication authentication) {
        AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal();
        return jwtTokenProvider.generateToken(authUserDetail);
    }

    private Authentication authenticate(Member member, String inputPassword) {
        AuthUserDetail authUserDetail = new AuthUserDetail(member);
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authUserDetail.getAccountId(),
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