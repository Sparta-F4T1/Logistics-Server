package com.logistic.auth.application.port.in.query;

import com.logistic.common.passport.model.Passport;
import org.springframework.data.domain.Pageable;

public record SearchUserQuery(
    String role,
    String status,
    Pageable pageable,
    Passport passport) {
}
