package com.multitap.auth.infrastructure;

import com.multitap.auth.entity.OAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OAuthRepository extends JpaRepository<OAuth, Long> {
    Optional<OAuth> findByMemberUuid(String memberUuid);
    Optional<OAuth> findByProviderAndProviderId(String provider, String providerId);
}
