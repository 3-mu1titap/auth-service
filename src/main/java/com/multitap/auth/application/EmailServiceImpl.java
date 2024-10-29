package com.multitap.auth.application;

import com.multitap.auth.common.exception.BaseException;
import com.multitap.auth.common.response.BaseResponseStatus;
import com.multitap.auth.dto.in.AuthCodeRequestDto;
import com.multitap.auth.dto.in.PasswordResetRequestDto;
import com.multitap.auth.entity.Member;
import com.multitap.auth.infrastructure.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    // 임시 비밀번호 전송
    @Override
    public void sendPasswordResetEmail(PasswordResetRequestDto passwordResetRequestDto) {
        Member member = memberRepository.findByEmail(passwordResetRequestDto.getAccountId()).orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));

        // 임시 비밀번호로 password 변경
        String temporaryPassword = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        memberRepository.save(passwordResetRequestDto.changeTemporaryPassword(passwordEncoder, member, temporaryPassword));

        sendTemporaryPasswordEmail(member.getEmail(), temporaryPassword);
    }

    // 인증코드 전송
    @Override
    public void sendAuthCodeEmail(HttpSession session) {
        String email = (String) session.getAttribute("email");
        String authCode = (String) session.getAttribute("authCode");
        sendAuthCodeEmail(email, authCode);
    }

    // 인증코드 검증
    @Override
    public void verifyAuthCode(AuthCodeRequestDto authCodeRequestDto, HttpSession session) {
        String authCode = (String) session.getAttribute("authCode");
        if (authCode == null || !authCode.equals(authCodeRequestDto.getAuthCode())) {
            throw new BaseException(BaseResponseStatus.AUTH_CODE_INVALID);
        }
    }

    @Override
    public void sendTemporaryPasswordEmail(String email, String temporaryPassword) {

        String subject = "임시 비밀번호 발급";
        String body = "귀하의 임시 비밀번호는 다음과 같습니다: " + temporaryPassword + "\n로그인 후 비밀번호를 변경해주세요.";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }

    @Override
    public void sendAuthCodeEmail(String email, String authCode) {

        String subject = "비밀번호 변경 인증코드";
        String body = "귀하의 인증코드는 " + authCode + "입니다.";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);
    }


}


