package com.multitap.auth.application;

import com.multitap.auth.common.exception.BaseException;
import com.multitap.auth.common.response.BaseResponseStatus;
import com.multitap.auth.dto.in.FindIdRequestDto;
import com.multitap.auth.dto.in.FindPasswordRequestDto;
import com.multitap.auth.entity.Member;
import com.multitap.auth.infrastructure.MemberRepository;
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
    public void sendTemporaryPasswordEmail(FindPasswordRequestDto findPasswordRequestDto) {
        Member member = memberRepository.findByAccountId(findPasswordRequestDto.getAccountId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));

        // 임시 비밀번호 생성 및 저장
        String temporaryPassword = generateTemporaryPassword();
        memberRepository.save(findPasswordRequestDto.changeTemporaryPassword(passwordEncoder, member, temporaryPassword));
        sendTemporaryPasswordToEmail(member.getEmail(), temporaryPassword);
    }

    // 아이디 전송
    @Override
    public void sendAccountIdEmail(FindIdRequestDto findIdRequestDto) {
        Member member = memberRepository.findByEmail(findIdRequestDto.getEmail())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));
        sendAccountEmailToUser(member.getEmail(), member.getAccountId());
    }

    // 임시 비밀번호 이메일 전송
    public void sendTemporaryPasswordToEmail(String email, String temporaryPassword) {
        String subject = "임시 비밀번호 발급";
        String body = String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body { 
                        font-family: 'Arial', sans-serif;
                        line-height: 1.6;
                        margin: 0;
                        padding: 0;
                    }
                    .container {
                        max-width: 600px;
                        margin: 0 auto;
                        padding: 20px;
                        background-color: #ffffff;
                    }
                    .header {
                        text-align: center;
                        padding: 20px 0;
                    }
                    .header img {
                        max-width: 200px;
                    }
                    .content {
                        background-color: #f8f9fa;
                        padding: 30px;
                        border-radius: 8px;
                        margin: 20px 0;
                    }
                    .password-box {
                        background-color: #ffffff;
                        padding: 15px;
                        border-radius: 5px;
                        border: 1px solid #dee2e6;
                        margin: 15px 0;
                        text-align: center;
                        font-size: 24px;
                        font-weight: bold;
                        color: #495057;
                    }
                    .footer {
                        text-align: center;
                        color: #6c757d;
                        font-size: 12px;
                        margin-top: 20px;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <img src="cid:logo" alt="Adaptors Logo">
                    </div>
                    <div class="content">
                        <h2>임시 비밀번호가 발급되었습니다</h2>
                        <p>안녕하세요,</p>
                        <p>요청하신 임시 비밀번호가 생성되었습니다. 보안을 위해 로그인 후 즉시 비밀번호를 변경해주세요.</p>
                        <div class="password-box">
                            %s
                        </div>
                        <p>보안을 위해 이 이메일은 곧바로 삭제해주시기 바랍니다.</p>
                    </div>
                    <div class="footer">
                        <p>© 2024 ADAPTORS. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
        """, temporaryPassword);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }

    // 인증코드로 아이디 전송
    public void sendAccountEmailToUser(String email, String accountId) {
        String subject = "기존 아이디 발급";
        String body = "귀하의 아이디는 " + accountId + "입니다.";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }

    // 임시 비밀번호 생성
    private String generateTemporaryPassword() {

        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }


}
