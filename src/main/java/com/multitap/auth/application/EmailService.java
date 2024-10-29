package com.multitap.auth.application;

import com.multitap.auth.dto.in.AuthCodeRequestDto;
import com.multitap.auth.dto.in.FindPasswordRequestDto;
import com.multitap.auth.dto.in.UuidRequestDto;
import jakarta.servlet.http.HttpSession;

public interface EmailService {
    void sendTemporaryPasswordEmail(FindPasswordRequestDto findPasswordRequestDto);
    void sendAuthCodeEmailToSession(UuidRequestDto uuidRequestDto, HttpSession session);
    void verifyAuthCode(AuthCodeRequestDto authCodeRequestDto, HttpSession session);
    void sendTemporaryPasswordToEmail(String email, String temporaryPassword);
    void sendAuthCodeEmailToUser(String email, String authCode);
}
