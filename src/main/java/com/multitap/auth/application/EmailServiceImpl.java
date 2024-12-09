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
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
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
        String body = "귀하의 임시 비밀번호는 다음과 같습니다: " + temporaryPassword + "\n로그인 후 비밀번호를 변경해주세요.";
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
