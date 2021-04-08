package io.asia.store.controller;

import io.asia.store.domain.dto.UserDto;
import io.asia.store.mapper.UserMapper;
import io.asia.store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto findUserById(@PathVariable Long id) {
        return userMapper.toDto(userService.findById(id));
    }

    @PostMapping
    @PreAuthorize("isAnonymous()")
    public UserDto saveUser(@RequestBody @Valid UserDto userDto) {
        return userMapper.toDto(userService.saveUser(userMapper.toDao(userDto)));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserDto> getPageUser(@RequestParam int page, @RequestParam int size) {
        return userService.getPage(PageRequest.of(page, size)).map(userMapper::toDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void removeUserById(@PathVariable Long id) {
        userService.removeById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated() && (hasRole('ADMIN') || @securityService.hasAccessToUser(#id))")
    public UserDto updateUser(@RequestBody @Valid UserDto user, @PathVariable Long id) {
        return userMapper.toDto(userService.update(userMapper.toDao(user), id));
    }

    @GetMapping("/current")
    @PreAuthorize("isAuthenticated()")
    public UserDto getCurrentUser() {
        return userMapper.toDto(userService.getCurrentUser());
    }
}


