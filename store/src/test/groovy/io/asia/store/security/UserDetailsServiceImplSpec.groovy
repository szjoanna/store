package io.asia.store.security

import io.asia.store.domain.dao.Role
import io.asia.store.domain.dao.User
import io.asia.store.repository.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import spock.lang.Specification

class UserDetailsServiceImplSpec extends Specification {
    def userDetailsService
    def userRepository = Mock(UserRepository)

    def setup() {
        userDetailsService = new UserDetailsServiceImpl(userRepository)
    }

    def 'testing loadUserByUsername method'() {
        given:
        List<Role> roles = new ArrayList<>()
        roles.add(new Role(2L, "Admin"))

        def user = User.builder()
                .id(2L)
                .email("email")
                .firstName("M")
                .secondName("MM")
                .password("a")
                .roles(roles)
                .build()

        userRepository.findByEmail("email") >> Optional.of(user)

        when:
        def result = userDetailsService.loadUserByUsername("email")

        then:
        result
    }

    def 'testing loadUserByUsername method when user is not exist'() {
        given:
        userRepository.findByEmail("email") >> Optional.empty()

        when:
        userDetailsService.loadUserByUsername("email")

        then:
        thrown(UsernameNotFoundException)
    }
}
