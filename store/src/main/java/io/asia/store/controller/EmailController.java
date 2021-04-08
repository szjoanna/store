package io.asia.store.controller;

import io.asia.store.flyweight.GeneratorFactoryEmail;
import io.asia.store.flyweight.domain.EmailType;
import io.asia.store.flyweight.generic.GenericFactory;
import io.asia.store.flyweight.generic.strategy.email.EmailStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emails")
@RequiredArgsConstructor
public class EmailController {
    private final GeneratorFactoryEmail generatorFactoryEmail;
    private final GenericFactory<EmailType, EmailStrategy> emailStrategy;

    @GetMapping
    void test (@RequestParam EmailType emailType) {
        generatorFactoryEmail.getValueByEmailType(emailType).generatedEmail();
    }

    @GetMapping("/generic")
    void testGeneric (@RequestParam EmailType emailType) {
        emailStrategy.getByKey(emailType).sendEmail();
    }
}
