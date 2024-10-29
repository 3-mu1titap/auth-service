package com.multitap.auth.application;

import com.multitap.auth.dto.in.AuthCodeRequestDto;
import com.multitap.auth.dto.in.PasswordResetRequestDto;
import jakarta.servlet.http.HttpSession;

public interface EmailService {
    void sendPasswordResetEmail(PasswordResetRequestDto passwordResetRequestDto);
    void sendAuthCodeEmail(HttpSession session);
    void verifyAuthCode(AuthCodeRequestDto authCodeRequestDto, HttpSession session);
    void sendTemporaryPasswordEmail(String email, String temporaryPassword);
    void sendAuthCodeEmail(String email,String authCode);
}
