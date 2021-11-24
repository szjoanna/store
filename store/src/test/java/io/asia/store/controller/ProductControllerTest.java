package io.asia.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.asia.store.domain.dao.Category;
import io.asia.store.domain.dao.Product;
import io.asia.store.domain.dao.User;
import io.asia.store.domain.dto.ProductDto;
import io.asia.store.mapper.ProductMapper;
import io.asia.store.repository.CategoryRepository;
import io.asia.store.repository.ProductRepository;
import io.asia.store.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.Collections;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductMapper productMapper;

    @WithMockUser(username = "m@m.w", roles = "ADMIN")
    @Test
    @Transactional
    void shouldGetPageProduct() throws Exception {
        User user = userRepository.save(User.builder()
                .firstName("a")
                .secondName("b")
                .email("m@m.w")
                .password("eA3*fgdsgfg")
                .roles(Collections.emptyList())
                .build());
        Category category = categoryRepository.save(Category.builder()
                .name("a")
                .build());
        productRepository.save(Product.builder()
                .name("a")
                .description("b")
                .price(2.0)
                .main(true)
                .deleted(false)
                .listOfProducts(Collections.emptyList())
                .category(category)
                .quantity(5.0)
                .imageUrl("a")
                .build());

        mockMvc.perform(get("/api/products")
                .queryParam("page", "0")
                .queryParam("size", "10")
                .queryParam("main", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    void shouldNotFindProductById() throws Exception {
        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldFindProductById() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("a")
                .version(1L)
                .build());
        Product product = productRepository.save(Product.builder()
                .name("a")
                .description("b")
                .price(2.0)
                .main(true)
                .deleted(false)
                .listOfProducts(Collections.emptyList())
                .category(category)
                .quantity(5.0)
                .imageUrl("a")
                .build());

        mockMvc.perform(get("/api/products/" + product.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotFindProductByMain() throws Exception {
        mockMvc.perform(get("/api/products/main")
                .queryParam("isMain", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").doesNotExist());
    }

    @Test
    void shouldFindProductByMain() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("a")
                .version(1L)
                .build());
        Product product = productRepository.save(Product.builder()
                .name("a")
                .description("b")
                .price(2.0)
                .main(true)
                .deleted(false)
                .listOfProducts(Collections.emptyList())
                .category(category)
                .quantity(5.0)
                .imageUrl("a")
                .build());

        mockMvc.perform(get("/api/products/main")
                .queryParam("isMain", "true"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldDeleteProductById() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("a")
                .version(1L)
                .build());
        Product product = productRepository.save(Product.builder()
                .name("a")
                .description("b")
                .price(2.0)
                .main(true)
                .deleted(false)
                .listOfProducts(Collections.emptyList())
                .category(category)
                .quantity(5.0)
                .imageUrl("a")
                .build());

        mockMvc.perform(delete("/api/products/" + product.getId()))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldNotDeleteProductByIdWhenProductInNotDB() throws Exception {
        mockMvc.perform(delete("/api/products/10"))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(roles = "USER")
    @Test
    void shouldNotDeleteProductByUserHasNotRoleAdmin() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("a")
                .version(1L)
                .build());
        Product product = productRepository.save(Product.builder()
                .name("a")
                .description("b")
                .price(2.0)
                .main(true)
                .deleted(false)
                .listOfProducts(Collections.emptyList())
                .category(category)
                .quantity(5.0)
                .imageUrl("a")
                .build());

        mockMvc.perform(delete("/api/products/" + product.getId()))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldUpdateProduct() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("a")
                .version(1L)
                .build());
        Product product = productRepository.save(Product.builder()
                .name("a")
                .description("b")
                .price(2.0)
                .main(true)
                .deleted(false)
                .listOfProducts(Collections.emptyList())
                .category(category)
                .quantity(5.0)
                .imageUrl("a")
                .build());

        mockMvc.perform(put("/api/products/" + product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(productMapper.toDto(product))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.description").value(product.getDescription()))
                .andExpect(jsonPath("$.price").value(product.getPrice()))
                .andExpect(jsonPath("$.quantity").value(product.getQuantity()));
    }

    @WithMockUser(roles = "USER")
    @Test
    void shouldNotUpdateProductWhenUserHasNotRoleAdmin() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("a")
                .version(1L)
                .build());
        Product product = productRepository.save(Product.builder()
                .name("a")
                .description("b")
                .price(2.0)
                .main(true)
                .deleted(false)
                .listOfProducts(Collections.emptyList())
                .category(category)
                .quantity(5.0)
                .imageUrl("a")
                .build());

        mockMvc.perform(put("/api/products/100")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(productMapper.toDto(product)))
        ).andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldNotUpdateProductWithoutRequiredFields() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("a")
                .version(1L)
                .build());
        Product product = productRepository.save(Product.builder()
                .name("")
                .description("b")
                .price(2.0)
                .main(true)
                .deleted(false)
                .listOfProducts(Collections.emptyList())
                .category(category)
                .quantity(5.0)
                .imageUrl("a")
                .build());

        mockMvc.perform(put("/api/products/" + product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(productMapper.toDto(product)))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldAutocompleteProduct() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("a")
                .version(1L)
                .build());
        Product product = productRepository.save(Product.builder()
                .name("CHLEB")
                .description("b")
                .price(2.0)
                .main(false)
                .deleted(false)
                .listOfProducts(Collections.emptyList())
                .category(category)
                .quantity(5.0)
                .imageUrl("a")
                .build());
        Product product1 = productRepository.save(Product.builder()
                .name("MLEKO")
                .description("b")
                .price(2.0)
                .main(false)
                .deleted(false)
                .listOfProducts(Collections.emptyList())
                .category(category)
                .quantity(5.0)
                .imageUrl("a")
                .build());

        mockMvc.perform(get("/api/products/autocomplete")
                .queryParam("value", "EB"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void shouldNotAutocompleteProduct() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("a")
                .version(1L)
                .build());
        Product product = productRepository.save(Product.builder()
                .name("CHLEB")
                .description("b")
                .price(2.0)
                .main(false)
                .deleted(false)
                .listOfProducts(Collections.emptyList())
                .category(category)
                .quantity(5.0)
                .imageUrl("a")
                .build());
        Product product1 = productRepository.save(Product.builder()
                .name("MLEKO")
                .description("b")
                .price(2.0)
                .main(false)
                .deleted(false)
                .listOfProducts(Collections.emptyList())
                .category(category)
                .quantity(5.0)
                .imageUrl("a")
                .build());

        mockMvc.perform(get("/api/products/autocomplete")
                .queryParam("value", "ZZ"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldSaveProduct() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("a")
                .version(1L)
                .build());
        ProductDto productDto = ProductDto.builder()
                .name("MLEKO")
                .description("b")
                .price(2.0)
                .main(false)
                .deleted(false)
                .subProducts(Collections.emptyList())
                .categoryId(category.getId())
                .quantity(5.0)
                .imageUrl("a")
                .build();
        InputStream file = getClass().getClassLoader().getResourceAsStream("assets/avatar.jpg");
        MockMultipartFile fileUpload = new MockMultipartFile("file", "avatar.jpg", MediaType.IMAGE_JPEG_VALUE, file);
        MockMultipartFile requestProductDto = new MockMultipartFile("productDto", "", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(productDto));

        mockMvc.perform(multipart("/api/products")
                .file(fileUpload)
                .file(requestProductDto)
                .with(procesor -> {
                    procesor.setMethod("POST");
                    return procesor;
                })
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @WithMockUser(roles = "USER")
    @Test
    void shouldNotSaveProductWhenUserHasNotRoleAdmin() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("a")
                .version(1L)
                .build());
        ProductDto productDto = ProductDto.builder()
                .name("MLEKO")
                .description("b")
                .price(2.0)
                .main(false)
                .deleted(false)
                .subProducts(Collections.emptyList())
                .categoryId(category.getId())
                .quantity(5.0)
                .imageUrl("a")
                .build();
        InputStream file = getClass().getClassLoader().getResourceAsStream("assets/avatar.jpg");
        MockMultipartFile fileUpload = new MockMultipartFile("file", "avatar.jpg", MediaType.IMAGE_JPEG_VALUE, file);
        MockMultipartFile requestProductDto = new MockMultipartFile("productDto", "", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(productDto));

        mockMvc.perform(multipart("/api/products")
                .file(fileUpload)
                .file(requestProductDto)
                .with(procesor -> {
                    procesor.setMethod("POST");
                    return procesor;
                })
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
