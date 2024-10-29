package com.multitap.auth.infrastructure;

import com.multitap.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByAccountId(String accountId);
    Optional<Member> findByEmail(String email);
}
