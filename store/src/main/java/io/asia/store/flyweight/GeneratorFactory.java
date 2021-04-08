package io.asia.store.flyweight;

import io.asia.store.flyweight.domain.FileType;
import io.asia.store.flyweight.strategy.GeneratorStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class GeneratorFactory {
    private final List<GeneratorStrategy> generatorStrategy;
    private Map<FileType, GeneratorStrategy> generatorStrategyMap;

    @PostConstruct
    void init() {
        this.generatorStrategyMap = generatorStrategy.stream().collect(Collectors.toMap(GeneratorStrategy::getFileType, generatorStrategy -> generatorStrategy));
    }

    public GeneratorStrategy getValueByFileType(FileType fileType) {
        return generatorStrategyMap.get(fileType);
    }
}
