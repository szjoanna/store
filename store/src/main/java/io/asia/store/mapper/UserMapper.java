package io.asia.store.mapper;

import io.asia.store.domain.dao.Role;
import io.asia.store.domain.dao.User;
import io.asia.store.domain.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper extends BasicMapper<UserDto, User>{
    @Mapping(target = "roles", ignore = true)
    User toDao(UserDto userDto);
    @Mapping(source = "roles", target = "roles", qualifiedByName = "rolesToNames")
    @Mapping(target = "password", ignore = true)
    UserDto toDto(User user);

    @Named("rolesToNames")
    default List<String> rolesToNames(List<Role> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }
}
