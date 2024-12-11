package com.multitap.auth.application;

import com.multitap.auth.entity.Member;
import java.util.Optional;
import org.springframework.stereotype.Service;


public interface MemberService {
    public String findMemberEmailByUuid(String uuid);
}
