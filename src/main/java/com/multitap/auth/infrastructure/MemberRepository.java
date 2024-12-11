package com.multitap.auth.infrastructure;

import com.multitap.auth.entity.Member;
import com.multitap.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByAccountId(String accountId);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByUuid(String uuid);
    @Query("SELECT m.uuid FROM Member m WHERE m.role = :role")
    List<String> findUuidsByRole(@Param("role") Role role);

}
