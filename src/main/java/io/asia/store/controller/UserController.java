package io.asia.store.controller;

import io.asia.store.domain.dao.User;
import io.asia.store.domain.dto.UserDto;
import io.asia.store.mapper.UserMapper;
import io.asia.store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

//adnotacja rejestrujaca bina w spirigu dlla kontrolera
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public UserDto findUserById(@PathVariable Long id) {
        return userMapper.toDto(userService.findById(id));
    }

    @PostMapping
    public UserDto saveUser(@RequestBody UserDto userDto) {
        return userMapper.toDto(userService.saveUser(userMapper.toDao(userDto)));
    }

    @GetMapping
    public Page<UserDto> getPageUser(@RequestParam int page, @RequestParam int size) {
        return userService.getPage(PageRequest.of(page, size)).map(userMapper:: toDto);
    }

    @DeleteMapping("/{id}")
    public void removeUserById(@PathVariable Long id) {
        userService.removeById(id);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@RequestBody UserDto userDto, @PathVariable Long id) {
        return userMapper.toDto(userService.update(userMapper.toDao(userDto), id));
    }

    @GetMapping("/current")
    public UserDto getCurrentUser() {
        return userMapper.toDto(userService.getCurrentUser());
    }
}


