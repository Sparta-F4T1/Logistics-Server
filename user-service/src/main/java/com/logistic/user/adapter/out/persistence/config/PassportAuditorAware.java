package com.logistic.user.adapter.out.persistence.config;

import static com.logistic.common.passport.constant.PassportConstant.ATTR_PASSPORT;

import com.logistic.common.passport.model.Passport;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Log4j2(topic = "auditor aware")
public class PassportAuditorAware implements AuditorAware<String> {

  private static final String DEFAULT_AUDITOR = "system";

  @Override
  public Optional<String> getCurrentAuditor() {
    try {
      ServletRequestAttributes attributes =
          (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

      if (attributes == null) {
        log.debug("요청 컨텍스트가 존재하지 않습니다.");
        return Optional.of(DEFAULT_AUDITOR);
      }

      HttpServletRequest request = attributes.getRequest();
      Passport passport = (Passport) request.getAttribute(ATTR_PASSPORT);

      if (passport == null) {
        log.debug("요청에서 패스포트를 찾을 수 없습니다.");
        return Optional.of(DEFAULT_AUDITOR);
      }

      return Optional.ofNullable(passport.getUserInfo().getUserId())
          .filter(userId -> !userId.isEmpty())
          .or(() -> Optional.of(DEFAULT_AUDITOR));
    } catch (Exception e) {
      log.warn("패스포트에서 현재 감사를 확인하는 중 오류 발생: ", e);
      return Optional.of(DEFAULT_AUDITOR);
    }
  }
}