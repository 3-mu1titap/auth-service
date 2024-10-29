package com.multitap.auth.presentation;

import com.multitap.auth.application.AuthService;
import com.multitap.auth.application.EmailService;
import com.multitap.auth.common.response.BaseResponse;
import com.multitap.auth.dto.in.*;
import com.multitap.auth.entity.AuthUserDetail;
import com.multitap.auth.vo.in.*;
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

import java.util.UUID;

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

    @Operation(summary = "비밀번호 찾기", description = "이메일 인증을 통해 임시 비밀번호를 발급 받습니다.")
    @PostMapping("/password-reset")
    public BaseResponse<Void> resetPassword(@RequestBody PasswordResetRequestVo passwordResetRequestVo) {
        emailService.sendPasswordResetEmail(PasswordResetRequestDto.from(passwordResetRequestVo));
        return new BaseResponse<>();
    }

    @Operation(summary = "비밀번호 변경 인증 요청", description = "요청을 통해 이메일로 인증코드를 보냅니다.")
    @PostMapping("/password-change")
    public BaseResponse<Void> changePassword(HttpSession session, @AuthenticationPrincipal AuthUserDetail authUserDetail) {
        session.setAttribute("authCode", UUID.randomUUID().toString().substring(0, 6));
        session.setAttribute("email", authUserDetail.getEmail());
        emailService.sendAuthCodeEmail(session);
        return new BaseResponse<>();
    }

    @Operation(summary = "인증코드 확인", description = "인증코드를 확인합니다.")
    @PostMapping("/verify-auth-code")
    public BaseResponse<Void> verifyAuthCode(@RequestBody AuthCodeRequestVo authCodeRequestVo, HttpSession session) {
        emailService.verifyAuthCode(AuthCodeRequestDto.from(authCodeRequestVo), session);
        return new BaseResponse<>();
    }

    @Operation(summary = "비밀번호 변경", description = "인증코드로 인증 후 비밀번호를 변경합니다.")
    @PostMapping("/change-password")
    public BaseResponse<Void> changePassword(@RequestBody PasswordChangeRequestDto passwordChangeRequestDto, HttpSession session, @AuthenticationPrincipal AuthUserDetail authUserDetail) {
        session.removeAttribute("email");
        session.removeAttribute("authCode"); // 인증 코드 제거
        emailService.changePassword(passwordRequestDto, authUserDetails.getMemberUuid());
        return new BaseResponse<>();
    }

}
