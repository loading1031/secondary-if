package com.secondaryif.server.global.security.util;

import com.secondaryif.server.global.security.dto.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
public class SecurityUtil {

    public static Long getMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("인증 정보가 존재하지 않습니다.");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails) {
            log.info("MemberId(): {}", ((CustomUserDetails) principal).getMemberId());
            return ((CustomUserDetails) principal).getMemberId();
        } else {
            throw new IllegalStateException("인증 정보가 올바르지 않습니다.");
        }
    }
}
