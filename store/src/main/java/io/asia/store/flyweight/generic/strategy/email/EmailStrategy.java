package io.asia.store.flyweight.generic.strategy.email;

import io.asia.store.flyweight.domain.EmailType;
import io.asia.store.flyweight.generic.strategy.GenericStrategy;

public interface EmailStrategy extends GenericStrategy <EmailType> {
    void sendEmail();
}
