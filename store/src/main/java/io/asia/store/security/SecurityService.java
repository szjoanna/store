package io.asia.store.security;

import io.asia.store.exception.UserNotLoggedException;
import io.asia.store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final UserService userService;

    public boolean hasAccessToUser(Long userId) {
        try {
            return userService.getCurrentUser().getId().equals(userId);
        } catch (UserNotLoggedException e) {
            return false;
        }
    }
}
