package io.asia.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.asia.store.domain.dao.User;
import io.asia.store.domain.dto.UserDto;
import io.asia.store.mapper.UserMapper;
import io.asia.store.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Test
    void shouldSaveUser() throws Exception {
        UserDto userDto = UserDto.builder()
                .firstName("a")
                .secondName("b")
                .email("c")
                .password("eA3*fgdsgfg")
                .build();

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.email").value(userDto.getEmail()))
                .andExpect(jsonPath("$.firstName").value(userDto.getFirstName()))
                .andExpect(jsonPath("$.secondName").value(userDto.getSecondName()));
    }

    @Test
    void shouldSaveUserWithExistingEmail() throws Exception {
        userRepository.save(User.builder().email("m@m.w").build());

        UserDto userDto = UserDto.builder()
                .firstName("a")
                .secondName("b")
                .email("m@m.w")
                .password("eA3*fgdsgfg")
                .build();

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userDto)))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldNotSaveUserWithoutRequiredFields() throws Exception {
        UserDto userDto = UserDto.builder()
                .firstName("")
                .secondName("")
                .email("")
                .password("")
                .build();

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userDto)))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "m@m.w")
    @Test
    void shouldSaveUserWhenUserIsLogin() throws Exception {
        userRepository.save(User.builder().email("m@m.w").build());

        UserDto userDto = UserDto.builder()
                .firstName("a")
                .secondName("b")
                .email("m@m.w")
                .password("eA3*fgdsgfg")
                .build();

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userDto)))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldGetPageUser() throws Exception {
        userRepository.save(User.builder()
                .email("a")
                .password("eA3*fgdsgfg")
                .firstName("c")
                .secondName("d")
                .build());

        mockMvc.perform(get("/api/users")
                .queryParam("page", "0")
                .queryParam("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @WithMockUser(roles = "USER")
    @Test
    void shouldGetPageUserWhenUserNotHaveRolesAdmin() throws Exception {
        userRepository.save(User.builder()
                .email("a")
                .password("eA3*fgdsgfg")
                .firstName("c")
                .secondName("d")
                .build());

        mockMvc.perform(get("/api/users")
                .queryParam("page", "0")
                .queryParam("size", "10"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldFindUserById() throws Exception {
        User user = userRepository.save(User.builder()
                .email("a")
                .password("eA3*fgdsgfg")
                .firstName("c")
                .secondName("d")
                .build());

        mockMvc.perform(get("/api/users/" + user.getId()))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldNotFindUserByIdWhenIdIsNotExisting() throws Exception {
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(roles = "USER")
    @Test
    void shouldNotFindUserByIdWhenUserNotHaveRolesAdmin() throws Exception {
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = "m@m.w", roles = "ADMIN")
    @Test
    void shouldDeleteUserById() throws Exception {
        User user = userRepository.save(User.builder()
                .email("m@m.w")
                .password("eA3*fgdsgfg")
                .firstName("c")
                .secondName("d")
                .build());

        mockMvc.perform(delete("/api/users/" + user.getId()))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "m@m.w", roles = "USER")
    @Test
    void shouldNotDeleteUserByIdWhenUserNotHasRolesAdmin() throws Exception {
        User user = userRepository.save(User.builder()
                .email("m@m.w")
                .password("eA3*fgdsgfg")
                .firstName("c")
                .secondName("d")
                .build());
        User user2 = userRepository.save(User.builder()
                .email("p@p.w")
                .password("eA3*fgdsgfg")
                .firstName("c")
                .secondName("d")
                .build());

        mockMvc.perform(delete("/api/users/" + user2.getId()))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldNotDeleteUserByIdWhenUserIsLogOut() throws Exception {
        User user = userRepository.save(User.builder()
                .email("m@m.w")
                .password("eA3*fgdsgfg")
                .firstName("c")
                .secondName("d")
                .build());

        mockMvc.perform(delete("/api/users/" + user.getId()))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    @Transactional
    void shouldUpdateUserHasRoleAdmin() throws Exception {
        User user = User.builder()
                .firstName("a")
                .secondName("b")
                .email("m@m.w")
                .password("eA3*fgdsgfg")
                .roles(Collections.emptyList())
                .build();

        user = userRepository.save(user);

        mockMvc.perform(put("/api/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userMapper.toDto(user))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.secondName").value(user.getSecondName()));
    }

    @WithMockUser(username = "m@m.w", roles = "USER")
    @Test
    void shouldNotUpdateUserHasNotRoleAdmin() throws Exception {
        User user = userRepository.save(User.builder()
                .firstName("a")
                .secondName("b")
                .email("m@m.w")
                .password("eA3*fgdsgfg")
                .roles(Collections.emptyList())
                .build());
        User user2 = userRepository.save(User.builder()
                .firstName("a")
                .secondName("b")
                .email("a@a.w")
                .password("eA3*fgdsgfg")
                .roles(Collections.emptyList())
                .build());

        mockMvc.perform(put("/api/users/" + user2.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userMapper.toDto(user))))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldNotUpdateUserWithoutRequiredFields() throws Exception {
        User user = userRepository.save(User.builder()
                .firstName("")
                .secondName("")
                .email("")
                .password("eA3*fgdsgfg")
                .roles(Collections.emptyList())
                .build());

        mockMvc.perform(put("/api/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userMapper.toDto(user))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotUpdateUserIfNotAuthenticatedAndNotAdmin() throws Exception {
        User user = userRepository.save(User.builder()
                .firstName("a")
                .secondName("b")
                .email("m@m.w")
                .password("eA3*fgdsgfg")
                .roles(Collections.emptyList())
                .build());

        mockMvc.perform(put("/api/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userMapper.toDto(user))))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = "m@m.w")
    @Test
    void shouldGetCurrentUserWhenUserIsLogIn() throws Exception {

        User user = User.builder()
                .firstName("a")
                .secondName("b")
                .email("m@m.w")
                .password("eA3*fgdsgfg")
                .build();

        userRepository.save(user);

        mockMvc.perform(get("/api/users/current"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetCurrentUserWhenUserIsLogOut() throws Exception {
        User user = User.builder()
                .firstName("a")
                .secondName("b")
                .email("m@m.w")
                .password("eA3*fgdsgfg")
                .build();
        userRepository.save(user);

        mockMvc.perform(get("/api/users/current"))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldGetCurrentUserWhenEmailIsNotExisting() throws Exception {
        mockMvc.perform(get("/api/users/current"))
                .andExpect(status().isForbidden());
    }
}
