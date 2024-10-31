package com.multitap.auth.application;

import com.multitap.auth.dto.in.*;
import com.multitap.auth.dto.out.SignInResponseDto;

public interface AuthService {
    void signUp(SignUpRequestDto signUpRequestDto);
    SignInResponseDto signIn(SignInRequestDto signInRequestDto);
    SignInResponseDto oAuthSignIn(OAuthSignInRequestDto oAuthSignInRequestDto);
    void verifyCurrentPassword(CurrentPasswordRequestDto currentPasswordRequestDto);
    void changePassword(NewPasswordRequestDto newPasswordRequestDto);
    void changeMemberInfo(MemberInfoRequestDto memberInfoRequestDto);
}
