package com.multitap.auth.application;


import com.multitap.auth.common.exception.BaseException;
import com.multitap.auth.common.response.BaseResponse;
import com.multitap.auth.common.response.BaseResponseStatus;
import com.multitap.auth.entity.Member;
import com.multitap.auth.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public String findMemberEmailByUuid(String uuid) {

        Member member = memberRepository.findByUuid(uuid).orElseThrow(
            () -> new BaseException(BaseResponseStatus.NO_EXIST_USER));
        return member.getEmail();
    }

}
