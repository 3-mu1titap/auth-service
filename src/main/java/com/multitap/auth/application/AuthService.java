package com.multitap.auth.application;

import com.multitap.auth.dto.in.*;
import com.multitap.auth.dto.out.FindIdResponseDto;
import com.multitap.auth.dto.out.SignInResponseDto;

public interface AuthService {
    void signUp(SignUpRequestDto signUpRequestDto);
    SignInResponseDto signIn(SignInRequestDto signInRequestDto);
    SignInResponseDto oAuthSignIn(OAuthSignInRequestDto oAuthSignInRequestDto);
    void signOut();
    FindIdResponseDto findId(FindIdRequestDto findIdRequestDto);
    void changePassword(PasswordChangeRequestDto passwordChangeRequestDto);
}
