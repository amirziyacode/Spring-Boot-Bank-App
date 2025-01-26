package org.example.bankapp.security.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationEvents {

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent authentication) {
        log.info("Authentication success: {}", authentication.getAuthentication().getName());
    }


    @EventListener
    public void onAuthenticationFailed(AbstractAuthenticationFailureEvent failure) {
        log.info("Authentication failure: {}  due to:{}", failure.getAuthentication().getName(), failure.getException().getMessage());
    }
}
