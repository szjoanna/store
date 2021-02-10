package io.asia.store.mapper.impl;

import io.asia.store.domain.dao.Role;
import io.asia.store.domain.dao.User;
import io.asia.store.domain.dto.UserDto;
import io.asia.store.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toDao(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .secondName(userDto.getSecondName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }

    @Override
    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .secondName(user.getSecondName())
                .email(user.getEmail())
                .roles(user.getRoles().stream()
                        .map(Role :: getName)
                        .collect(Collectors.toList()))
                .build();
    }
}