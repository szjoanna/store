package io.asia.store.service.impl

import io.asia.store.domain.dao.Role
import io.asia.store.domain.dao.User
import io.asia.store.exception.UserNotLoggedException
import io.asia.store.fixtures.Fixtures
import io.asia.store.repository.RoleRepository
import io.asia.store.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.security.crypto.password.PasswordEncoder

import javax.persistence.EntityNotFoundException

class UserServiceImplSpec extends Fixtures {
    def userService
    def userRepository = Mock(UserRepository)
    def roleRepository = Mock(RoleRepository)
    def passwordEncoder = Mock(PasswordEncoder)

    def setup() {
        userService = new UserServiceImpl(userRepository, passwordEncoder, roleRepository)
    }

    def 'test findById user'() {
        given:
        User user = User.builder().build()
        userRepository.findById(1) >> Optional.of(user)

        when:
        def result = userService.findById(1)

        then:
        result.equals(user)
    }

    def 'should not findById user'() {
        given:
        userRepository.findById(1)  >> Optional.empty()

        when:
        userService.findById(1)

        then:
        thrown EntityNotFoundException
    }

    def 'test remove user by id'() {
        given:
        userRepository.deleteById(1) >> null

        when:
        userService.removeById(1)

        then:
        1 * userRepository.deleteById(1)
    }

    def 'test save user'() {
        given:
        def user = new User(firstName: "a", secondName: "b", email: "c", password: "d")
        userRepository.save(_ as User) >> new User()
        passwordEncoder.encode(user.getPassword()) >> "abc"
        roleRepository.findByName("ROLE_USER") >> Optional.of(new Role(1, "a"))

        when:
        def result = userService.saveUser(user)

        then:
        result != null
    }

    def 'test update user'() {
        given:
        userRepository.findById(1) >> Optional.of(new User())

        when:
        userService.update(new User(), 1)

        then:
        1 * userRepository.save(_)
    }

    def 'should not update user where user not in DB'() {
        given:
        userRepository.findById(1) >> Optional.empty()

        when:
        def result = userService.update(new User(), 1)

        then:
        thrown EntityNotFoundException
    }

    def 'test getUserPage'() {
        given:
        userRepository.findAll(PageRequest.of(1,3)) >> Page.empty()

        when:
        def result = userService.getPage(PageRequest.of(1,3))

        then:
        result != null
    }

    def "when user is not logged should throw UserNotLoggedException"() {
        given:
        mockUser('')

        def optionalUser = Optional.empty()
        userRepository.findByEmail(_) >> optionalUser

        when:
        userService.getCurrentUser()

        then:
        thrown(UserNotLoggedException.class)
    }

    def "when user is logged it should return current user"() {
        given:
        mockUser('')
        def user = new User(password: "test", email: "test@gmail.com")
        def userDB = Optional.of(user)
        userRepository.findByEmail(_) >> userDB

        when:
        def result = userService.getCurrentUser()

        then:
        result == user
    }
}
