package com.multitap.auth.application;

import com.multitap.auth.dto.in.OAuthSignInRequestDto;
import com.multitap.auth.dto.in.SignInRequestDto;
import com.multitap.auth.dto.in.SignUpRequestDto;
import com.multitap.auth.dto.out.SignInResponseDto;

public interface AuthService {
    void signUp(SignUpRequestDto signUpRequestDto);
    SignInResponseDto signIn(SignInRequestDto signInRequestDto);
    SignInResponseDto oAuthSignIn(OAuthSignInRequestDto oAuthSignInRequestDto);

}
