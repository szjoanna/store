package io.asia.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.asia.store.domain.dao.*;
import io.asia.store.domain.dto.CategoryDto;
import io.asia.store.mapper.UserOrderMapper;
import io.asia.store.repository.*;
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

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
public class UserOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserOrderRepository userOrderRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private UserOrderMapper userOrderMapper;

    @WithMockUser(username = "m@m.w", roles = "ADMIN")
    @Test
    void shouldCreateUserOrder() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("a")
                .build());
        User user = userRepository.save(User.builder()
                .firstName("a")
                .secondName("b")
                .email("m@m.w")
                .password("eA3*fgdsgfg")
                .roles(Collections.emptyList())
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
                .user(user)
                .product(product)
                .build());
        UserOrder userOrder = userOrderRepository.save(UserOrder.builder()
                .quantity(2.0)
                .product(product)
                .user(user)
                .orderNumber("abc")
                .build());

        mockMvc.perform(post("/api/orders"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotCreateUserOrderWhenUserIsNotLogin() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("a")
                .build());
        User user = userRepository.save(User.builder()
                .firstName("a")
                .secondName("b")
                .email("m@m.w")
                .password("eA3*fgdsgfg")
                .roles(Collections.emptyList())
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
                .user(user)
                .product(product)
                .build());
        UserOrder userOrder = userOrderRepository.save(UserOrder.builder()
                .quantity(2.0)
                .product(product)
                .user(user)
                .orderNumber("abc")
                .build());

        mockMvc.perform(post("/api/orders"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = "m@m.w", roles = "ADMIN")
    @Test
    void shouldGetOrders() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("a")
                .build());
        User user = userRepository.save(User.builder()
                .firstName("a")
                .secondName("b")
                .email("m@m.w")
                .password("eA3*fgdsgfg")
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
                .user(user)
                .product(product)
                .build());
        UserOrder userOrder = userOrderRepository.save(UserOrder.builder()
                .quantity(2.0)
                .product(product)
                .user(user)
                .orderNumber("62dd5b7c-3eb0-468a-9593-ac6daf766b6e")
                .createdDate(LocalDateTime.of(2020, 12, 12, 12, 12))
                .build());

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetOrdersWhenUserIsNotLogin() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("a")
                .build());
        User user = userRepository.save(User.builder()
                .firstName("a")
                .secondName("b")
                .email("m@m.w")
                .password("eA3*fgdsgfg")
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
                .user(user)
                .product(product)
                .build());
        UserOrder userOrder = userOrderRepository.save(UserOrder.builder()
                .quantity(2.0)
                .product(product)
                .user(user)
                .orderNumber("62dd5b7c-3eb0-468a-9593-ac6daf766b6e")
                .createdDate(LocalDateTime.of(2020, 12, 12, 12, 12))
                .build());

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = "m@m.w", roles = "ADMIN")
    @Test
    void shouldGetOrderDetails() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("a")
                .build());
        User user = userRepository.save(User.builder()
                .firstName("a")
                .secondName("b")
                .email("m@m.w")
                .password("eA3*fgdsgfg")
                .roles(Collections.emptyList())
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
                .user(user)
                .product(product)
                .build());
        UserOrder userOrder = userOrderRepository.save(UserOrder.builder()
                .quantity(2.0)
                .product(product)
                .user(user)
                .orderNumber("abc")
                .createdDate(LocalDateTime.of(2020, 12, 12, 12, 12))
                .build());

        mockMvc.perform(get("/api/orders/details")
                .queryParam("orderNumber", "abc")
                .queryParam("page", "0")
                .queryParam("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetOrderDetailsWhenUserIsNotLogin() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("a")
                .build());
        User user = userRepository.save(User.builder()
                .firstName("a")
                .secondName("b")
                .email("m@m.w")
                .password("eA3*fgdsgfg")
                .roles(Collections.emptyList())
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
                .user(user)
                .product(product)
                .build());
        UserOrder userOrder = userOrderRepository.save(UserOrder.builder()
                .quantity(2.0)
                .product(product)
                .user(user)
                .orderNumber("abc")
                .createdDate(LocalDateTime.of(2020, 12, 12, 12, 12))
                .build());

        mockMvc.perform(get("/api/orders/details")
                .queryParam("orderNumber", "abc")
                .queryParam("page", "0")
                .queryParam("size", "10"))
                .andExpect(status().isForbidden());
    }
}
