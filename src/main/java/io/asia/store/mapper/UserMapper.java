package io.asia.store.mapper;

import io.asia.store.domain.dao.User;
import io.asia.store.domain.dto.UserDto;

public interface UserMapper {
    User toDao(UserDto userDto);
    UserDto toDto(User user);
}
