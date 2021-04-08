package io.asia.store.flyweight.strategy;

import io.asia.store.flyweight.domain.EmailType;
import lombok.Getter;

public abstract class GeneratorStrategyEmail {
    @Getter
    private EmailType emailType;

    protected GeneratorStrategyEmail(EmailType emailType) {
        this.emailType = emailType;
    }

    public abstract byte[] generatedEmail();
}
