package io.asia.store.security

import io.asia.store.domain.dao.Role
import io.asia.store.domain.dao.User
import io.asia.store.exception.UserNotLoggedException
import io.asia.store.fixtures.Fixtures
import io.asia.store.service.UserService

class SecurityServiceSpec extends Fixtures {
    def securityService
    def userService = Mock(UserService)

    def setup() {
        securityService = new SecurityService(userService)
    }

    def 'testing hasAccessToUser method'() {
        given:
        Set<Role> roles = new HashSet<>()
        roles.add(new Role(5L, "Admin"))
        userService.getCurrentUser() >> new User(id: 1L, firstName: "a", secondName: "b", email: "c", password: "d")

        when:
        def result = securityService.hasAccessToUser(1L)

        then:
        result
    }

    def ' testing hasAccessToUser for an exception'() {
        given:
        userService.getCurrentUser() >> { throw new UserNotLoggedException() }

        when:
        def result = securityService.hasAccessToUser(1L)

        then:
        !result
    }
}
