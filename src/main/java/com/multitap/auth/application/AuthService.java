package com.multitap.auth.application;

import com.multitap.auth.dto.in.*;
import com.multitap.auth.dto.out.RefreshTokenResponseDto;
import com.multitap.auth.dto.out.SignInResponseDto;
import com.multitap.auth.dto.out.UuidResponseDto;

public interface AuthService {
    UuidResponseDto signUp(SignUpRequestDto signUpRequestDto);
    void checkAccountId(AccountIdRequestDto accountIdRequestDto);
    SignInResponseDto signIn(SignInRequestDto signInRequestDto);
    SignInResponseDto oAuthSignIn(OAuthSignInRequestDto oAuthSignInRequestDto);
    void changePassword(NewPasswordRequestDto newPasswordRequestDto);
    void changeMemberInfo(MemberInfoRequestDto memberInfoRequestDto);
    RefreshTokenResponseDto refreshAccess(RefreshTokenRequestDto refreshTokenRequestDto);
}
