package io.asia.store.config;

import io.asia.store.domain.dao.Role;
import io.asia.store.repository.RoleRepository;
import io.asia.store.service.impl.WatchServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class AppConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    CommandLineRunner commandLineRunner(RoleRepository roleRepository, WatchServiceImpl watchService) {
        return args -> {
            Optional<Role> optionalRole = roleRepository.findByName("ROLE_USER");
            if(!optionalRole.isPresent()) {
                roleRepository.save(new Role(null, "ROLE_USER"));
            }
            new Thread(watchService, "watcher-service").start();
        };
    }
}
