package io.asia.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.asia.store.domain.dao.Category;
import io.asia.store.domain.dto.CategoryDto;
import io.asia.store.mapper.CategoryMapper;
import io.asia.store.repository.CategoryRepository;
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
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldSaveCategory() throws Exception {
        CategoryDto categoryDto = CategoryDto.builder()
                .name("a")
                .version(1L)
                .build();

        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(categoryDto)))
        .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(categoryDto.getName()))
                .andExpect(jsonPath("$.version").value(categoryDto.getVersion()));
    }

    @WithMockUser(roles = "USER")
    @Test
    void shouldNotSaveCategoryWhenUserNotHaveRolesAdmin() throws Exception {
        CategoryDto categoryDto = CategoryDto.builder()
                .name("a")
                .version(1L)
                .build();

        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(categoryDto)))
        .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldNotSaveCategoryWithoutRequiredFields() throws Exception {
        CategoryDto categoryDto = CategoryDto.builder()
                .name("")
                .version(1L)
                .build();

        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(categoryDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetPageCategory() throws Exception {
        Category category = Category.builder()
                .name("a")
                .version(1L)
                .build();
        categoryRepository.save(category);

        mockMvc.perform(get("/api/categories")
                .queryParam("page", "0")
                .queryParam("size", "10"))
        .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    void shouldNotFindCategoryById() throws Exception {
        mockMvc.perform(get("/api/categories/1"))
        .andExpect(status().isNotFound());
    }

    @Test
    void shouldFindCategoryById() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("a")
                .version(1L)
                .build());

        mockMvc.perform(get("/api/categories/" + category.getId()))
        .andExpect(status().isOk());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldDeleteCategoryById() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("a")
                .version(1L)
                .build());

        mockMvc.perform(delete("/api/categories/" + category.getId())
        ).andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Test
    void shouldDeleteCategoryByIdWhenUserNotHaveRolesAdmin() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("a")
                .version(1L)
                .build());

        mockMvc.perform(delete("/api/categories/" + category.getId())
        ).andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldUpdateCategory() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("a")
                .version(1L)
                .build());

        mockMvc.perform(put("/api/categories/" + category.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(categoryMapper.toDto(category))))
        .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(category.getId()))
                .andExpect(jsonPath("$.name").value(category.getName()))
                .andExpect(jsonPath("$.version").value(category.getVersion()));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldNotUpdateUserWhenCategoryIsNotExist() throws Exception {
        CategoryDto categoryDto = CategoryDto.builder()
                .name("a")
                .version(1L)
                .build();

        mockMvc.perform(put("/api/categories/100")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(categoryDto)))
        .andExpect(status().isNotFound());
    }

    @WithMockUser(roles = "USER")
    @Test
    void shouldUpdateCategoryWhenUserNotHaveRolesAdmin() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("a")
                .version(1L)
                .build());

        mockMvc.perform(put("/api/categories/" + category.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(categoryMapper.toDto(category))))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "USER")
    @Test
    void shouldUpdateCategoryWhenUserWithoutRequiredFields() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("")
                .version(1L)
                .build());

        mockMvc.perform(put("/api/categories/" + category.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(categoryMapper.toDto(category))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetAllCategories() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("a")
                .version(1L)
                .build());
        mockMvc.perform(get("/api/categories/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}
