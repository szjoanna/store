package io.asia.store.mapper.impl

import io.asia.store.domain.dao.Product
import io.asia.store.domain.dto.ProductDto
import spock.lang.Specification

class ProductMapperImplSpec extends Specification{
    def mapper = new ProductMapperImpl();

    def 'test toDao product'() {
        given:
        ProductDto productDto = new ProductDto(1L, "a", "b", 2.5, 1L, null, true, 1L)

        when:
        def result = mapper.toDao(productDto);

        then:
        result != null
    }

    def 'test toDto product where listOfProducts is null'() {
        given:
        Product product = new Product(1L, "a", "b", 2.5, true, null, 1L, null, null, null)

        when:
        def result = mapper.toDto(product);

        then:
        result != null
    }

    def 'test toDto product where listOfProducts is not null'() {
        given:
        Product product1 = new Product(1L, "a", "b", 2.5, true, null, 1L, null, null, null)
        Product product2 = new Product(1L, "a", "b", 2.5, true, null, 1L, null, null, null)
        Product product3 = new Product(1L, "a", "b", 2.5, true, null, 1L, null, null, null)
        List<ProductDto> listOfProducts = new ArrayList<>()
        listOfProducts.add(product1)
        listOfProducts.add(product2)
        listOfProducts.add(product3)
        Product product4 = new Product(1L, "a", "b", 2.5, true, listOfProducts, 1L, null, null, null)

        when:
        def result = mapper.toDto(product4)

        then:
        result != null
    }
}
