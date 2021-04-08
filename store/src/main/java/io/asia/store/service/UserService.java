package io.asia.store.service;

import io.asia.store.domain.dao.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User saveUser(User user);
    User findById(Long id);
    void removeById(Long id);
    Page<User> getPage(Pageable pageable);
    User update(User user, Long id);
    User getCurrentUser();
}
