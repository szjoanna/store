package io.asia.store.service.impl

import io.asia.store.domain.dao.Basket
import io.asia.store.domain.dao.Product
import io.asia.store.domain.dao.User
import io.asia.store.repository.BasketRepository
import spock.lang.Specification

class BasketServiceImplSpec extends Specification {
    def basketService
    def basketRepository = Mock(BasketRepository)
    def userService = Mock(UserServiceImpl)
    def productService = Mock(ProductServiceImpl)

    def setup() {
        basketService = new BasketServiceImpl(userService, basketRepository, productService)
    }

    def 'test get basketsList by current user when basket is empty'() {
        given:
        userService.getCurrentUser() >> new User(id: 3L)
        basketRepository.findByUserId(1L) >> Collections.emptyList()

        when:
        def result = basketService.getBasketByCurrentUser()

        then:
        result == null
    }

    def 'test get basketsList by current user when basket is not empty'() {
        given:
        userService.getCurrentUser() >> new User(id: 3L)
        def basket = Basket.builder().build()
        basketRepository.findByUserId(3L) >> Collections.singletonList(basket)

        when:
        def result = basketService.getBasketByCurrentUser()

        then:
        result != null
    }

    def 'test delete all current user products'() {
        given:
        when:
        def result = basketService.deleteAllCurrentUserProducts()

        then:
        1 * basketRepository.deleteAll()
    }

    def 'test delete product from basket'() {
        given:
        when:
        basketService.deleteProductFromBasket(1)

        then:
        1 * basketRepository.deleteByProductId(1)
    }

    def 'test add product to basket when quantity is not bigger than available quantity and product is already in basket'() {
        given:
        def product = Product.builder()
                .id(1L)
                .name("a")
                .description("b")
                .price(2.5)
                .main(true)
                .listOfProducts(Collections.emptyList())
                .version(1L)
                .category(null)
                .quantity(5.0)
                .imageUrl("image_test")
                .deleted(false)
                .build()
        def user = User.builder()
                .id(1L)
                .firstName("a")
                .secondName("b")
                .email("a")
                .password("a")
                .version(1L)
                .build()
        def basket = Basket.builder()
                .id(1L)
                .quantity(2.0)
                .product(product)
                .user(user)
                .createdDate(null)
                .build()
        userService.getCurrentUser() >> user
        productService.findById(1L) >> product
        basketRepository.findByUserId(1L) >> Collections.singletonList(basket)

        when:
        basketService.addProductToBasket(1L, 2.0)

        then:
        1 * basketRepository.save(_)
    }

    def 'test add product to basket quantity is bigger than available quantity and product is already in basket'() {
        given:
        def product = Product.builder()
                .id(1L)
                .name("a")
                .description("b")
                .price(2.5)
                .main(true)
                .listOfProducts(Collections.emptyList())
                .version(1L)
                .category(null)
                .quantity(5.0)
                .imageUrl("image_test")
                .deleted(false)
                .build()
        def user = User.builder()
                .id(1L)
                .firstName("a")
                .secondName("b")
                .email("a")
                .password("a")
                .version(1L)
                .build()
        def basket = Basket.builder()
                .id(1L)
                .quantity(5.0)
                .product(product)
                .user(user)
                .createdDate(null)
                .build()
        userService.getCurrentUser() >> user
        productService.findById(1L) >> product
        basketRepository.findByUserId(1L) >> Collections.singletonList(basket)

        when:
        basketService.addProductToBasket(1L, 2.0)

        then:
        thrown IllegalArgumentException
    }

    def 'test add product to basket quantity is not bigger than available quantity and product is not in basket'() {
        given:
        def product = Product.builder()
                .id(1L)
                .name("a")
                .description("b")
                .price(2.5)
                .main(true)
                .listOfProducts(Collections.emptyList())
                .version(1L)
                .category(null)
                .quantity(5.0)
                .imageUrl("image_test")
                .deleted(false)
                .build()
        def user = User.builder()
                .id(1L)
                .firstName("a")
                .secondName("b")
                .email("a")
                .password("a")
                .version(1L)
                .build()
        userService.getCurrentUser() >> user
        productService.findById(1L) >> product
        basketRepository.findByUserId(1L) >> Collections.emptyList()

        when:
        basketService.addProductToBasket(1L, 2.0)

        then:
        1 * basketRepository.save(_)
    }

    def 'test add product to basket quantity is bigger than available quantity and product is not in basket'() {
        given:
        def product = Product.builder()
                .id(1L)
                .name("a")
                .description("b")
                .price(2.5)
                .main(true)
                .listOfProducts(Collections.emptyList())
                .version(1L)
                .category(null)
                .quantity(1.0)
                .imageUrl("image_test")
                .deleted(false)
                .build()
        def user = User.builder()
                .id(1L)
                .firstName("a")
                .secondName("b")
                .email("a")
                .password("a")
                .version(1L)
                .build()
        userService.getCurrentUser() >> user
        productService.findById(1L) >> product
        basketRepository.findByUserId(1L) >> Collections.emptyList()

        when:
        basketService.addProductToBasket(1L, 2.0)

        then:
        thrown IllegalArgumentException
    }

    def 'test update product from basket when quantity is not bigger than available quantity'() {
        given:
        def product = Product.builder()
                .id(1L)
                .name("a")
                .description("b")
                .price(2.5)
                .main(true)
                .listOfProducts(Collections.emptyList())
                .version(1L)
                .category(null)
                .quantity(5.0)
                .imageUrl("image_test")
                .deleted(false)
                .build()
        def user = User.builder().build()
        def basket = Basket.builder()
                .id(1L)
                .quantity(2.0)
                .product(product)
                .user(user)
                .createdDate(null)
                .build()
        userService.getCurrentUser() >> new User(id: 1L)
        basketRepository.findByUserId(1L) >> Collections.singletonList(basket)

        when:
        basketService.updateProductFromBasket(1L, 3.0)

        then:
        basket.getQuantity() == 3.0
        1 * basketRepository.save(_)
    }

    def 'test update product from basket when quantity is bigger than available quantity'() {
        given:
        def product = Product.builder()
                .id(1L)
                .name("a")
                .description("b")
                .price(2.5)
                .main(true)
                .listOfProducts(Collections.emptyList())
                .version(1L)
                .category(null)
                .quantity(2.0)
                .imageUrl("image_test")
                .deleted(false)
                .build()
        def user = User.builder().build()
        def basket = Basket.builder()
                .id(1L)
                .quantity(2.0)
                .product(product)
                .user(user)
                .createdDate(null)
                .build()
        userService.getCurrentUser() >> new User(id: 1L)
        basketRepository.findByUserId(1L) >> Collections.singletonList(basket)

        when:
        basketService.updateProductFromBasket(1L, 3.0)

        then:
        thrown IllegalArgumentException
    }

    def 'test update product from basket when product is not available'() {
        given:
        userService.getCurrentUser() >> new User(id: 1L)
        basketRepository.findByUserId(1L) >> Collections.emptyList()

        when:
        basketService.updateProductFromBasket(1L, 3.0)

        then:
        thrown IllegalArgumentException
    }
}
