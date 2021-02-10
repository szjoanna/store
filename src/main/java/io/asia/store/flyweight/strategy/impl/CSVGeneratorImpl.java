package io.asia.store.flyweight.strategy.impl;

import io.asia.store.flyweight.domain.FileType;
import io.asia.store.flyweight.strategy.GeneratorStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CSVGeneratorImpl extends GeneratorStrategy {
    protected CSVGeneratorImpl() {
        super(FileType.CSV);
    }

    @Override
    public byte[] generatedFile() {
        log.info("CSV");
        return new byte[0];
    }
}
