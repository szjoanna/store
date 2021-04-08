package io.asia.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.asia.store.domain.dao.Basket;
import io.asia.store.domain.dao.Category;
import io.asia.store.domain.dao.Product;
import io.asia.store.domain.dao.User;
import io.asia.store.mapper.BasketMapper;
import io.asia.store.repository.BasketRepository;
import io.asia.store.repository.CategoryRepository;
import io.asia.store.repository.ProductRepository;
import io.asia.store.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
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
public class BasketControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BasketMapper basketMapper;

    @Test
    void shouldNotGetBasketByCurrentUserWhenUserIsNotLogged() throws Exception {
        mockMvc.perform(get("/api/baskets"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = "m@m.w")
    @Test
    void shouldGetBasketByCurrentUser() throws Exception {
        User user = userRepository.save(User.builder()
                .firstName("a")
                .secondName("b")
                .email("m@m.w")
                .password("d")
                .roles(Collections.emptyList())
                .build());
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
        Basket basket = basketRepository.save(Basket.builder()
                .quantity(2.0)
                .product(product)
                .user(user)
                .build());

        mockMvc.perform(get("/api/baskets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @WithMockUser(username = "m@m.w")
    @Test
    @Transactional
    void shouldAddProductToBasket() throws Exception {
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
        User user = userRepository.save(User.builder()
                .firstName("a")
                .secondName("b")
                .email("m@m.w")
                .password("d")
                .roles(Collections.emptyList())
                .build());

        mockMvc.perform(post("/api/baskets")
                .queryParam("idProduct", product.getId().toString())
                .queryParam("quantity", "1.0"))
        .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value("1.0"));
    }

    @Test
    void shouldNotAddProductToBasketWhenUserIsNotLogged() throws Exception {
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
        User user = userRepository.save(User.builder()
                .firstName("a")
                .secondName("b")
                .email("m@m.w")
                .password("d")
                .roles(Collections.emptyList())
                .build());

        mockMvc.perform(post("/api/baskets")
                .queryParam("idProduct", product.getId().toString())
                .queryParam("quantity", "1.0"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = "m@m.w")
    @Test
    void shouldDeleteProductFromBasketById() throws Exception {
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
        User user = userRepository.save(User.builder()
                .firstName("a")
                .secondName("b")
                .email("m@m.w")
                .password("d")
                .roles(Collections.emptyList())
                .build());
        Basket basket = basketRepository.save(Basket.builder()
                .quantity(2.0)
                .product(product)
                .user(user)
                .build());

        mockMvc.perform(delete("/api/baskets/" + product.getId())
        ).andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteProductFromBasketByIdWhenUserIsNotLogged() throws Exception {
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
        User user = userRepository.save(User.builder()
                .firstName("a")
                .secondName("b")
                .email("m@m.w")
                .password("d")
                .roles(Collections.emptyList())
                .build());
        Basket basket = basketRepository.save(Basket.builder()
                .quantity(2.0)
                .product(product)
                .user(user)
                .build());

        mockMvc.perform(delete("/api/baskets/" + product.getId())
        ).andExpect(status().isForbidden());
    }

    @WithMockUser(username = "m@m.w")
    @Test
    void shouldDeleteAllProductsFromBasket() throws Exception {
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
        User user = userRepository.save(User.builder()
                .firstName("a")
                .secondName("b")
                .email("m@m.w")
                .password("d")
                .roles(Collections.emptyList())
                .build());
        Basket basket = basketRepository.save(Basket.builder()
                .quantity(2.0)
                .product(product)
                .user(user)
                .build());

        mockMvc.perform(delete("/api/baskets")
        ).andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteAllProductsFromBasketWhenUserIsNotLogged() throws Exception {
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
        User user = userRepository.save(User.builder()
                .firstName("a")
                .secondName("b")
                .email("m@m.w")
                .password("d")
                .roles(Collections.emptyList())
                .build());
        Basket basket = basketRepository.save(Basket.builder()
                .quantity(2.0)
                .product(product)
                .user(user)
                .build());

        mockMvc.perform(delete("/api/baskets")
        ).andExpect(status().isForbidden());
    }

    @WithMockUser(username = "m@m.w")
    @Test
    void shouldUpdateProductFromBasket() throws Exception {
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
        User user = userRepository.save(User.builder()
                .firstName("a")
                .secondName("b")
                .email("m@m.w")
                .password("d")
                .roles(Collections.emptyList())
                .build());
        Basket basket = basketRepository.save(Basket.builder()
                .quantity(2.0)
                .product(product)
                .user(user)
                .build());

        mockMvc.perform(put("/api/baskets/" + product.getId())
                .queryParam("quantity", "1.0"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotUpdateProductFromBasketWhenUserIsNotLogIn() throws Exception {
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
        User user = userRepository.save(User.builder()
                .firstName("a")
                .secondName("b")
                .email("m@m.w")
                .password("d")
                .roles(Collections.emptyList())
                .build());
        Basket basket = basketRepository.save(Basket.builder()
                .quantity(2.0)
                .product(product)
                .user(user)
                .build());

        mockMvc.perform(put("/api/baskets/" + product.getId())
                .queryParam("quantity", "1.0"))
                .andExpect(status().isForbidden());
    }
}
