package io.asia.store.flyweight.strategy.impl;

import io.asia.store.flyweight.domain.EmailType;
import io.asia.store.flyweight.strategy.GeneratorStrategyEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RemaindPasswordGeneratorImpl extends GeneratorStrategyEmail {
    protected RemaindPasswordGeneratorImpl() {
        super(EmailType.REMIND_PASSWORD);
    }

    @Override
    public byte[] generatedEmail() {
        log.info("REMIND_PASSWORD");
        return new byte[0];
    }
}
