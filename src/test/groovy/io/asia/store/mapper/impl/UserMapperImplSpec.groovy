package io.asia.store.mapper.impl

import io.asia.store.domain.dao.User
import io.asia.store.domain.dto.UserDto
import spock.lang.Specification

class UserMapperImplSpec extends Specification{
    def mapper = new UserMapperImpl()

    def 'test toDao user'() {
        given:
        UserDto userDto = new UserDto(1L, "a", "b", "c", "e")

        when:
        def result = mapper.toDao(userDto)

        then:
        result != null
    }

    def 'test toDto user'() {
        given:
        User user = new User(1L, "a", "b", "c", "d", 2L, null, null, null)

        when:
        def result = mapper.toDto(user)

        then:
        result != null
    }
}
