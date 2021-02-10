package io.asia.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.asia.store.domain.dao.User;
import io.asia.store.domain.dto.UserDto;
import io.asia.store.mapper.UserMapper;
import io.asia.store.repository.UserRepository;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//do odpalenia testów springa
@SpringBootTest
//możliwość pytania endpoitów w controlerze, wstrzykujemy mock MVC
@AutoConfigureMockMvc
//do czyszczenia bazy danych, przeładowanie za każdym testem
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//do czyszczenia bazy danych
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
//ładuje plik z konfiguracją yml
@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
public class UserControllerTest {
    //wstrzykiwanie
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Test
    void shutSaveUser() throws Exception {
        UserDto userDto = UserDto.builder()
                .firstName("a")
                .secondName("b")
                .email("c")
                .password("d")
                .build();

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userDto))
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.email").value(userDto.getEmail()))
                .andExpect(jsonPath("$.firstName").value(userDto.getFirstName()))
                .andExpect(jsonPath("$.secondName").value(userDto.getSecondName()));
    }

    @Test
    void shutGetPageUser() throws Exception {
        userRepository.save(User.builder()
                .email("a")
                .password("b")
                .firstName("c")
                .secondName("d")
                .build());

        mockMvc.perform(get("/api/users")
                .queryParam("page", "0")
                .queryParam("size", "10")
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    void shutNotFindUserById() throws Exception {
        mockMvc.perform(get("/api/users/1")
        ).andExpect(status().isNotFound());
    }

    @Test
    void shutFindUserById() throws Exception {
        User user = userRepository.save(User.builder()
                .email("a")
                .password("b")
                .firstName("c")
                .secondName("d")
                .build());

        mockMvc.perform(get("/api/users/" + user.getId())
        ).andExpect(status().isOk());
    }

    @Test
    void shutDeleteUserById() throws Exception {
        User user = userRepository.save(User.builder()
                .email("a")
                .password("b")
                .firstName("c")
                .secondName("d")
                .build());

        mockMvc.perform(delete("/api/users/" + user.getId())
        ).andExpect(status().isOk());
    }

    @Test
    void shutUpdateUser() throws Exception {
        User user = User.builder()
                .firstName("a")
                .secondName("b")
                .email("c")
                .password("d")
                .build();

        user = userRepository.save(user);

        mockMvc.perform(put("/api/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userMapper.toDto(user)))
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.secondName").value(user.getSecondName()));
    }

    @Test
    void shutNotUpdateUser() throws Exception {
        UserDto userDto = UserDto.builder()
                .firstName("a")
                .secondName("b")
                .email("c")
                .password("d")
                .build();

        mockMvc.perform(put("/api/users/100")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userDto))
        ).andExpect(status().isNotFound());
    }

//    @Test
//    void shutNotSaveUser() throws Exception {
//        User user = User.builder()
//                .firstName("a")
//                .secondName("b")
//                .email("c")
//                .password("d")
//                .build();
//        JdbcSQLIntegrityConstraintViolationException
//
//        userRepository.save(user);
//        UserDto userDto = userMapper.toDto(user);
//        userDto.setPassword("a");
//        mockMvc.perform(post("/api/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsBytes(userDto))
//        ).andExpect(status().isConflict());
//    }
}
