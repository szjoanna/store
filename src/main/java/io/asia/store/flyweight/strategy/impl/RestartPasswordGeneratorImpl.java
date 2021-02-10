package io.asia.store.flyweight.strategy.impl;

import io.asia.store.flyweight.domain.EmailType;
import io.asia.store.flyweight.domain.FileType;
import io.asia.store.flyweight.strategy.GeneratorStrategyEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RestartPasswordGeneratorImpl extends GeneratorStrategyEmail {
    protected RestartPasswordGeneratorImpl() {
        super(EmailType.RESTART_PASSWORD);
    }
    @Override
    public byte[] generatedEmail() {
        log.info("RESTART_PASSWORD");
        return new byte[0];
    }
}

