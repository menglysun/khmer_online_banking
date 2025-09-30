package org.springclass.onlinebankingsystem.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;
import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditingConfig implements AuditorAware<UserDetails> {

    @Override
    public Optional<UserDetails> getCurrentAuditor() {
        var auditor = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(auditor) || !auditor.isAuthenticated()) {
            return Optional.empty();
        } else if (auditor.getPrincipal() instanceof UserDetails) {
            return Optional.of((UserDetails) auditor.getPrincipal());
        }
        return Optional.empty();
    }
}
