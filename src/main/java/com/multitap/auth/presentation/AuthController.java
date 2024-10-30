package com.multitap.auth.presentation;

import com.multitap.auth.application.AuthService;
import com.multitap.auth.application.EmailService;
import com.multitap.auth.common.response.BaseResponse;
import com.multitap.auth.dto.in.*;
import com.multitap.auth.entity.AuthUserDetail;
import com.multitap.auth.vo.in.*;
import com.multitap.auth.vo.out.FindIdResponseVo;
import com.multitap.auth.vo.out.SignInResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "계정 관리 API", description = "계정 관련 API endpoints")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;

    @Operation(summary = "회원가입", description = "회원가입 기능입니다.")
    @PostMapping("/sign-up")
    public BaseResponse<Void> signUp(@RequestBody SignUpRequestVo signUpRequestVo) {
        authService.signUp(SignUpRequestDto.from(signUpRequestVo));
        return new BaseResponse<>();
    }

    @Operation(summary = "로그인", description = "로그인 기능입니다.")
    @PostMapping("/sign-in")
    public BaseResponse<SignInResponseVo> signIn(@RequestBody SignInRequestVo signInRequestVo) {
        return new BaseResponse<>(authService.signIn(SignInRequestDto.from(signInRequestVo)).toVo());
    }

    @Operation(summary = "소셜 로그인", description = "소셜 로그인 기능입니다.")
    @PostMapping("/oauth-sign-in")
    public BaseResponse<SignInResponseVo> oAuthSignIn(@RequestBody OAuthSignInRequestVo oAuthSignInRequestVo) {
        return new BaseResponse<>(authService.oAuthSignIn(OAuthSignInRequestDto.from(oAuthSignInRequestVo)).toVo());
    }

    //todo: kafka를 통한 token 블랙 리스트 구현
    // @Operation(summary = "로그아웃", description = "로그아웃 기능입니다.")
    // @PostMapping("/sign-out")
    // public BaseResponse<Void> signOut(@AuthenticationPrincipal AuthUserDetail authUserDetail) {
    //     authService.signOut(authUserDetail.getMemberUuid());
    //     return new BaseResponse<>();
    // }

    @Operation(summary = "아이디 찾기", description = "이메일 인증을 통해 아이디를 찾습니다.")
    @PostMapping("/find-id")
    public BaseResponse<Void> findId(@RequestBody FindIdRequestVo findIdRequestVo) {
        emailService.sendAccountIdEmail(FindIdRequestDto.from(findIdRequestVo));
        return new BaseResponse<>();
    }

    @Operation(summary = "비밀번호 찾기", description = "이메일 인증을 통해 임시 비밀번호를 발급 받습니다.")
    @PostMapping("/reset-password")
    public BaseResponse<Void> resetPassword(@RequestBody FindPasswordRequestVo findPasswordRequestVo) {
        emailService.sendTemporaryPasswordEmail(FindPasswordRequestDto.from(findPasswordRequestVo));
        return new BaseResponse<>();
    }


    @Operation(summary = "현재 비밀번호 확인", description = "비밀번호 재설정을 위해 현재 비밀번호를 확인합니다.")
    @PostMapping("/verify-current-password")
    public BaseResponse<Void> verifyCurrentPassword(@RequestBody CurrentPasswordRequestDto currentPasswordRequestDto, HttpSession session, @AuthenticationPrincipal AuthUserDetail authUserDetail) {
        authService.verifyCurrentPassword(currentPasswordRequestDto);
        return new BaseResponse<>();
    }

    @Operation(summary = "비밀번호 재설정", description = "비밀번호를 재설정합니다.")
    @PostMapping("/change-password")
    public BaseResponse<Void> changePassword(@RequestBody NewPasswordRequestDto newPasswordRequestDto) {
        authService.changePassword(newPasswordRequestDto);
        return new BaseResponse<>();
    }

}
