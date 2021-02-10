package io.asia.store.flyweight.strategy.impl;

import io.asia.store.flyweight.domain.EmailType;
import io.asia.store.flyweight.strategy.GeneratorStrategyEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RegisterUserGeneratorImpl extends GeneratorStrategyEmail {
    protected RegisterUserGeneratorImpl() {
        super(EmailType.REGISTER_USER);
    }

    @Override
    public byte[] generatedEmail() {
        log.info("REGISTER_USER");
        return new byte[0];
    }
}
