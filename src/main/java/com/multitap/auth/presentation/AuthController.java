package com.multitap.auth.presentation;

import com.multitap.auth.application.AuthService;
import com.multitap.auth.application.BlackListService;
import com.multitap.auth.application.DataInsertService;
import com.multitap.auth.application.EmailService;
import com.multitap.auth.common.jwt.JwtTokenProvider;
import com.multitap.auth.common.response.BaseResponse;
import com.multitap.auth.dto.in.*;
import com.multitap.auth.dto.out.MentorUuidResponseDto;
import com.multitap.auth.vo.in.*;
import com.multitap.auth.vo.out.MentorUuidResponseVo;
import com.multitap.auth.vo.out.RefreshTokenResponseVo;
import com.multitap.auth.vo.out.SignInResponseVo;
import com.multitap.auth.vo.out.UuidResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "계정 관리 API", description = "계정 관련 API endpoints")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;
    private final DataInsertService dataInsertService;
    private final BlackListService blackListService;
    private final JwtTokenProvider jwtTokenProvider;
    
    //todo: ec2 데이터 값 넣기

    @Operation(summary = "회원가입", description = "회원가입 기능입니다.")
    @PostMapping("/sign-up")
    public BaseResponse<UuidResponseVo> signUp(@RequestBody SignUpRequestVo signUpRequestVo) {
        return new BaseResponse<>(authService.signUp(SignUpRequestDto.from(signUpRequestVo)).toVo());
    }

    @Operation(summary = "아이디 확인", description = "아이디 중복을 확인합니다.")
    @PostMapping("/cheak-accountId")
    public BaseResponse<Void> checkAccountId(@RequestBody AccountIdRequestVo accountIdRequestVo) {
        authService.checkAccountId(AccountIdRequestDto.from(accountIdRequestVo));
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

    @Operation(summary = "로그아웃", description = "로그아웃 기능입니다. refresh token 을 넘겨주세요")
    @PostMapping("/sign-out")
    public BaseResponse<Void> signOut(@RequestHeader("Authorization") String token) {
        log.info("token: {}", token);
        String jwtToken = token.substring(7);
        long expiration = jwtTokenProvider.getExpiration(jwtToken);
        blackListService.addToBlacklist(jwtToken, expiration);
        return new BaseResponse<>();
    }

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
    
    @Operation(summary = "비밀번호 재설정", description = "비밀번호를 재설정합니다. refresh token을 넘겨주세요")
    @PostMapping("/change-password")
    public BaseResponse<Void> changePassword(@RequestBody NewPasswordRequestVo newPasswordRequestVo, @RequestHeader("Authorization") String token, @RequestHeader("userUuid") String uuid) {
        authService.changePassword(NewPasswordRequestDto.from(newPasswordRequestVo, uuid));
        String jwtToken = token.substring(7);
        long expiration = jwtTokenProvider.getExpiration(jwtToken);
        blackListService.addToBlacklist(jwtToken, expiration);
        return new BaseResponse<>();
    }

    @Operation(summary = "회원 정보 수정", description = "닉네임 또는 전화번호를 수정합니다.")
    @PutMapping("/change-memberInfo")
    public BaseResponse<Void> changeMemberInfo(@RequestBody MemberInfoRequestVo memberInfoRequestVo, @RequestHeader("userUuid") String uuid) {
        authService.changeMemberInfo(MemberInfoRequestDto.from(memberInfoRequestVo, uuid));
        return new BaseResponse<>();
    }

    @Operation(summary = "access token 재발급", description = "만료된 access token을 재발급 합니다.")
    @PostMapping("/refresh-access")
    public BaseResponse<RefreshTokenResponseVo> refreshAccess(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        return new BaseResponse<>(authService.refreshAccess(RefreshTokenRequestDto.from(jwtToken)).toVo());
    }

    @Operation(summary = "전체 멘토 uuid 조회", description = "회원가입 된 모든 멘토 uuid를 조회합니다")
    @GetMapping("/mentor")
    public BaseResponse<MentorUuidResponseVo> refreshAccess() {
        MentorUuidResponseDto mentorUuidResponseDto = authService.getMentorUuid();
        return new BaseResponse<>(mentorUuidResponseDto.toVo(mentorUuidResponseDto));
    }

    @Tag(name = "회원 더미 데이터 저장 API", description = "csv 파일로 데이터 저장")
    @PostMapping(value = "/data", consumes = "multipart/form-data")
    public BaseResponse<Void> addData(@RequestParam("file") MultipartFile file) {
        dataInsertService.addMemberFromCsv(file);
        return new BaseResponse<>();
    }
}
