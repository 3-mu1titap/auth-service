package com.multitap.auth.application;

import com.multitap.auth.dto.in.FindIdRequestDto;
import com.multitap.auth.dto.in.FindPasswordRequestDto;

public interface EmailService {
    void sendTemporaryPasswordEmail(FindPasswordRequestDto findPasswordRequestDto);
    void sendTemporaryPasswordToEmail(String email, String temporaryPassword);
    void sendAccountIdEmail(FindIdRequestDto findIdRequestDto);
}
