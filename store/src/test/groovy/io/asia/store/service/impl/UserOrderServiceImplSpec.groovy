package io.asia.store.service.impl

import io.asia.store.domain.dao.*
import io.asia.store.repository.UserOrderRepository
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class UserOrderServiceImplSpec extends Specification {
    def userOrderService
    def userOrderRepository = Mock(UserOrderRepository)
    def basketService = Mock(BasketServiceImpl)
    def userService = Mock(UserServiceImpl)
    def productService = Mock(ProductServiceImpl)

    def setup() {
        userOrderService = new UserOrderServiceImpl(userOrderRepository, basketService, userService, productService)
    }

    def 'test get orders when ordersList is empty'() {
        given:
        userService.getCurrentUser() >> new User(id: 3L)
        userOrderRepository.findOrders(3L) >> Collections.emptyList()

        when:
        def result = userOrderService.getOrders()

        then:
        result == []
    }

    def 'test get ordersDetails'() {
        given:
        def userOrder = UserOrder.builder()
                .id(1L)
                .quantity(2.0)
                .product(Product.builder().build())
                .user(User.builder().build())
                .createdDate(null)
                .orderNumber("abc")
                .build()
        userOrderRepository.findByOrderNumber("abc", PageRequest.of(1, 1)) >> new PageImpl([userOrder])

        when:
        def result = userOrderService.getOrderDetails("abc", PageRequest.of(1, 1))

        then:
        result == new PageImpl([userOrder])
    }

    def 'test create order when product quantity in basket is more than available quantity product'() {
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
                .quantity(5.0)
                .product(product)
                .user(user)
                .createdDate(null)
                .build()
        basketService.getBasketByCurrentUser() >> Collections.singletonList(basket)

        when:
        userOrderService.createOrder()

        then:
        thrown IllegalArgumentException
    }

    def 'test create order'() {
        given:
        def category = Category.builder()
                .id(1L)
                .name("a")
                .version(1L)
                .build()
        def product = Product.builder()
                .id(1L)
                .name("a")
                .description("b")
                .price(2.5)
                .main(true)
                .listOfProducts(Collections.emptyList())
                .version(1L)
                .category(category)
                .quantity(2.0)
                .imageUrl("image_test")
                .deleted(false)
                .build()
        def user = User.builder().build()
        def basket = Basket.builder()
                .id(1L)
                .quantity(1.0)
                .product(product)
                .user(user)
                .createdDate(null)
                .build()
        basketService.getBasketByCurrentUser() >> Collections.singletonList(basket)

        when:
        userOrderService.createOrder()

        then:
        1 * userOrderRepository.saveAll(_);
    }
}
