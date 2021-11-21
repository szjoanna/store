package io.asia.store.mapper.impl

import io.asia.store.domain.dao.User
import io.asia.store.domain.dto.UserDto
import io.asia.store.mapper.UserMapperImpl
import spock.lang.Specification

class UserMapperImplSpec extends Specification {
    def mapper = new UserMapperImpl()

    def 'test toDao user'() {
        given:
        UserDto userDto = UserDto.builder()
                .id(1L)
                .firstName("a")
                .secondName("b")
                .email("c")
                .password("e")
                .roles(Collections.emptyList())
                .build()

        when:
        def result = mapper.toDao(userDto)

        then:
        result.firstName.equals(userDto.firstName)
        result.secondName.equals(userDto.secondName)
        result.email.equals(userDto.email)
        result.password.equals(userDto.password)
    }

    def 'test toDto user'() {
        given:
        User user = User.builder()
                .id(1L)
                .firstName("a")
                .secondName("b")
                .email("c")
                .password("d")
                .version(2L)
                .createdDate(null)
                .lastModifiedDate(null)
                .roles(Collections.emptyList())
                .build()

        when:
        def result = mapper.toDto(user)

        then:
        result.firstName.equals(user.firstName)
        result.secondName.equals(user.secondName)
        result.email.equals(user.email)
    }
}
