package io.asia.store.flyweight.generic.strategy.file.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.asia.store.flyweight.domain.FileType;
import io.asia.store.flyweight.generic.strategy.file.GeneratorStrategy;
import io.asia.store.flyweight.generic.strategy.file.exception.FileGenerateException;
import io.asia.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class JsonGeneratorGenericImpl implements GeneratorStrategy {
    private final ObjectMapper objectMapper;
    private final ProductRepository productRepository;

    @Override
    public byte[] generatorFile() throws FileGenerateException {
        log.info("JSON");
        try {
            return objectMapper.writeValueAsBytes(productRepository.findAll());
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        throw new FileGenerateException("Excel can not generated");
    }

    @Override
    public FileType getType() {
        return FileType.JSON;
    }
}
