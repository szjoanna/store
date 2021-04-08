package io.asia.store.mapper.impl

import io.asia.store.domain.dao.Basket
import io.asia.store.domain.dao.Product
import io.asia.store.domain.dao.User
import io.asia.store.domain.dto.BasketDto
import spock.lang.Specification

class BasketMapperImplSpec extends Specification {
    def mapper = new BasketMapperImpl()

    def 'test toDao basket'() {
        given:
        Product product = Product.builder().build()
        User user = User.builder().build()
        BasketDto basketDto = BasketDto.builder()
                .id(1L)
                .quantity(2.0)
                .product(product)
                .user(user)
                .build()

        when:
        def result = mapper.toDao(basketDto)

        then:
        result.quantity.equals(basketDto.quantity)
        result.product.equals(basketDto.product)
        result.user.equals(basketDto.user)
    }

    def 'test toDto basket'() {
        given:
        Product product = Product.builder().build()
        User user = User.builder().build()
        Basket basket = Basket.builder()
                .id(1L)
                .quantity(2.0)
                .user(user)
                .product(product)
                .build()

        when:
        def result = mapper.toDto(basket)

        then:
        result.quantity.equals(basket.quantity)
        result.product.equals(basket.product)
        result.user.equals(basket.user)
    }

    def 'test toDto listBackets'() {
        given:
        Product product = Product.builder().build()
        User user = User.builder().build()
        Basket basket1 = Basket.builder()
                .id(1L)
                .quantity(2.0)
                .user(user)
                .product(product)
                .build()
        Basket basket2 = Basket.builder()
                .id(4L)
                .quantity(4.0)
                .user(user)
                .product(product)
                .build()
        List<Basket> baskets = Arrays.asList(basket1, basket2)

        when:
        def result = mapper.listBasketsToDto(baskets)

        then:
        result.get(0).quantity.equals(baskets.get(0).quantity)
        result.get(0).product.equals(baskets.get(0).product)
        result.get(0).user.equals(baskets.get(0).user)
        result.get(1).quantity.equals(baskets.get(1).quantity)
        result.get(1).product.equals(baskets.get(1).product)
        result.get(1).user.equals(baskets.get(1).user)
    }
}
