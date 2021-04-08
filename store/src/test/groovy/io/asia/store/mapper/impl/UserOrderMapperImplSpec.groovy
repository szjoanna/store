package io.asia.store.mapper.impl


import io.asia.store.domain.dao.Product
import io.asia.store.domain.dao.User
import io.asia.store.domain.dao.UserOrder
import io.asia.store.domain.dto.ProductDto
import io.asia.store.mapper.ProductMapper
import spock.lang.Specification

class UserOrderMapperImplSpec extends Specification {

    def mapper
    def productMapper = Mock(ProductMapper)

    def setup() {
        mapper = new UserOrderMapperImpl(productMapper)
    }

    def 'test toDto userOrder'() {
        given:
        ProductDto productDto = ProductDto.builder()
                .id(4L)
                .name("a")
                .description("b")
                .price(2.5)
                .main(true)
                .subProducts(Collections.emptyList())
                .version(1L)
                .categoryId(2L)
                .quantity(5.0)
                .imageUrl("image_test")
                .deleted(false)
                .build()
        Product product = Product.builder()
                .id(1L)
                .name("a")
                .description("b")
                .price(2.5)
                .main(true)
                .listOfProducts(Collections.emptyList())
                .version(1L)
                .createdDate(null)
                .lastModifiedDate(null)
                .category(null)
                .quantity(6.0)
                .imageUrl("image_test")
                .deleted(false)
                .build()
        User user = User.builder().build()
        UserOrder userOrder = UserOrder.builder()
                .id(1L)
                .user(user)
                .product(product)
                .quantity(2.0)
                .createdDate(null)
                .orderNumber("123a")
                .build()

        new UserOrder()

        when:
        productMapper.toDto(_) >> productDto
        def result = mapper.userOrderToDto(userOrder)

        then:
        result.quantity.equals(userOrder.getQuantity())
    }
}
