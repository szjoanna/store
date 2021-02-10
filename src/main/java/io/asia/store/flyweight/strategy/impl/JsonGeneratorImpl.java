package io.asia.store.flyweight.strategy.impl;

import io.asia.store.flyweight.domain.FileType;
import io.asia.store.flyweight.strategy.GeneratorStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JsonGeneratorImpl extends GeneratorStrategy {
    protected JsonGeneratorImpl() {
        super(FileType.JSON);
    }

    @Override
    public byte[] generatedFile() {
        log.info("JSON");
        return new byte[0];
    }
}
