package com.multitap.auth.presentation;

import com.multitap.auth.application.AuthService;
import com.multitap.auth.common.response.BaseResponse;
import com.multitap.auth.dto.in.OAuthSignInRequestDto;
import com.multitap.auth.dto.in.SignInRequestDto;
import com.multitap.auth.dto.in.SignUpRequestDto;
import com.multitap.auth.vo.in.OAuthSignInRequestVo;
import com.multitap.auth.vo.in.SignInRequestVo;
import com.multitap.auth.vo.in.SignUpRequestVo;
import com.multitap.auth.vo.out.SignInResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = """
            code: String, 6자리 이상
                
            password: 비밀번호는 최소 8자 이상, 영문자, 숫자, 특수문자를 각각 1개 이상 포함해야 합니다.
                
            role: MENTEE, MENTOR, ADMIN 중에서 택 1
            """)
    @PostMapping("/sign-up")
    public BaseResponse<Void> signUp(
            @RequestBody SignUpRequestVo signUpRequestVo) {
        log.info("signUpRequestVo : {}", signUpRequestVo);
        authService.signUp(SignUpRequestDto.from(signUpRequestVo));
        return new BaseResponse<>();
    }

    @Operation(summary = "로그인", description = """
            로그인 기능입니다.
            """)
    @PostMapping("/sign-in")
    public BaseResponse<SignInResponseVo> signIn(
            @RequestBody SignInRequestVo signInRequestVo) {

        return new BaseResponse<>(
                authService.signIn(SignInRequestDto.from(signInRequestVo)).toVo()
        );
    }

    @Operation(summary = "소셜 로그인", description = "소셜 로그인 기능입니다.", tags = {"AuthUserDetail"})
    @PostMapping("/oauth-sign-in")
    public BaseResponse<SignInResponseVo> oAuthSignIn(
            @RequestBody OAuthSignInRequestVo oAuthSignInRequestVo) {

        return new BaseResponse<>(
                authService.oAuthSignIn(OAuthSignInRequestDto.of(oAuthSignInRequestVo)).toVo()
        );
    }


}
