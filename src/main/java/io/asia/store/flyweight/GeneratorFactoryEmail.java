package io.asia.store.flyweight;

import io.asia.store.flyweight.domain.EmailType;
import io.asia.store.flyweight.strategy.GeneratorStrategyEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class GeneratorFactoryEmail {
    private final List<GeneratorStrategyEmail> generatorStrategyEmail;
    private Map<EmailType, GeneratorStrategyEmail> generatorStrategyEmailMap;

    @PostConstruct
    void init() {
        this.generatorStrategyEmailMap = generatorStrategyEmail.stream().collect(Collectors.toMap(GeneratorStrategyEmail::getEmailType, generatorStrategyEmail -> generatorStrategyEmail));
    }

    public GeneratorStrategyEmail getValueByEmailType(EmailType emailType) {
        return generatorStrategyEmailMap.get(emailType);
    }
}
