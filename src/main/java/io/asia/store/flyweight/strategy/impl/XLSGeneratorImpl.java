package io.asia.store.flyweight.strategy.impl;

import io.asia.store.flyweight.domain.FileType;
import io.asia.store.flyweight.strategy.GeneratorStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class XLSGeneratorImpl extends GeneratorStrategy {
    protected XLSGeneratorImpl() {
        super(FileType.XLS);
    }

    @Override
    public byte[] generatedFile() {
        log.info("XLS");
        return new byte[0];
    }
}
