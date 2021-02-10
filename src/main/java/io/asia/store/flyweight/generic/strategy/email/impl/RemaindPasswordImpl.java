package io.asia.store.flyweight.generic.strategy.email.impl;

import io.asia.store.flyweight.domain.EmailType;
import io.asia.store.flyweight.generic.strategy.email.EmailStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RemaindPasswordImpl implements EmailStrategy {
    @Override
    public void sendEmail() {
        log.info("remaindPassword");
    }

    @Override
    public EmailType getType() {
        return EmailType.REMIND_PASSWORD;
    }
}
