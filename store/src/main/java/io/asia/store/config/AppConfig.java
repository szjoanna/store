package io.asia.store.config;

import io.asia.store.domain.dao.Role;
import io.asia.store.repository.RoleRepository;
import io.asia.store.service.impl.WatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Slf4j
@Configuration
public class AppConfig {
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    CommandLineRunner commandLineRunner(RoleRepository roleRepository, WatchServiceImpl watchService) {
        return args -> {
//            log.info(userName);
//            log.info(password);
//            Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER");
//            if(!optionalRoleUser.isPresent()) {
//                roleRepository.save(new Role(null, "ROLE_USER"));
//            }
//            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
//            if(!optionalRoleAdmin.isPresent()) {
//                roleRepository.save(new Role(null, "ROLE_ADMIN"));
//            }
            new Thread(watchService, "watcher-service").start();
        };
    }
}
