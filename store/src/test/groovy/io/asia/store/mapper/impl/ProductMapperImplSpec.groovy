package io.asia.store.mapper.impl

import io.asia.store.domain.dao.Category
import io.asia.store.domain.dao.Product
import io.asia.store.domain.dto.ProductDto
import spock.lang.Specification

class ProductMapperImplSpec extends Specification {
    def mapper = new ProductMapperImpl();

    def 'test toDao product'() {
        given:
        ProductDto productDto = ProductDto.builder()
                .id(1L)
                .name("a")
                .description("b")
                .price(2.5)
                .version(1L)
                .subProducts(Collections.emptyList())
                .main(true)
                .categoryId(1L)
                .quantity(1.0)
                .deleted(false)
                .imageUrl("test_url")
                .build()

        when:
        def result = mapper.toDao(productDto);

        then:
        result.name.equals(productDto.name)
        result.description.equals(productDto.description)
        result.price.equals(productDto.price)
        result.version.equals(productDto.version)
        result.main.equals(productDto.main)
        result.quantity.equals(productDto.quantity)
        result.deleted.equals(productDto.deleted)
        result.imageUrl.equals(productDto.imageUrl)
    }

    def 'test toDto product where listOfProducts is null'() {
        given:
        Category category = Category.builder()
                .id(1L)
                .name("test_name")
                .version(1L)
                .createdDate(null)
                .lastModifiedDate(null)
                .build();
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
                .category(category)
                .quantity(2.0)
                .imageUrl("image_test")
                .deleted(false)
                .build()

        when:
        def result = mapper.toDto(product);

        then:
        result.name.equals(product.name)
        result.description.equals(product.description)
        result.price.equals(product.price)
        result.main.equals(product.main)
        result.version.equals(product.version)
        result.categoryId.equals(product.category.id)
        result.quantity.equals(product.quantity)
        result.imageUrl.equals(product.imageUrl)
        result.deleted.equals(product.deleted)
    }

    def 'test toDto product where listOfProducts is not null'() {
        given:
        Category category = Category.builder()
                .id(1L)
                .name("test_name")
                .version(1L)
                .createdDate(null)
                .lastModifiedDate(null)
                .build();
        Product product1 = Product.builder()
                .id(1L)
                .name("a")
                .description("b")
                .price(2.5)
                .main(true)
                .listOfProducts(Collections.emptyList())
                .version(1L)
                .createdDate(null)
                .lastModifiedDate(null)
                .category(category)
                .quantity(2.0)
                .imageUrl("image_test")
                .deleted(false)
                .build()
        Product product2 = Product.builder()
                .id(2L)
                .name("a")
                .description("b")
                .price(2.5)
                .main(true)
                .listOfProducts(Collections.emptyList())
                .version(1L)
                .createdDate(null)
                .lastModifiedDate(null)
                .category(category)
                .quantity(2.0)
                .imageUrl("image_test")
                .deleted(false)
                .build()
        Product product3 = Product.builder()
                .id(3L)
                .name("a")
                .description("b")
                .price(2.5)
                .main(true)
                .listOfProducts(Collections.emptyList())
                .version(1L)
                .createdDate(null)
                .lastModifiedDate(null)
                .category(category)
                .quantity(2.0)
                .imageUrl("image_test")
                .deleted(false)
                .build()
        List<ProductDto> listOfProducts = Arrays.asList(product1, product2, product3)
        Product product4 = Product.builder()
                .id(4L)
                .name("a")
                .description("b")
                .price(2.5)
                .main(true)
                .listOfProducts(listOfProducts)
                .version(1L)
                .createdDate(null)
                .lastModifiedDate(null)
                .category(category)
                .quantity(2.0)
                .imageUrl("image_test")
                .deleted(false)
                .build()

        when:
        def result = mapper.toDto(product4)

        then:
        result.name.equals(product4.name)
        result.description.equals(product4.description)
        result.price.equals(product4.price)
        result.main.equals(product4.main)
        result.version.equals(product4.version)
        result.categoryId.equals(product4.category.id)
        result.quantity.equals(product4.quantity)
        result.imageUrl.equals(product4.imageUrl)
        result.deleted.equals(product4.deleted)
        ! result.subProducts.equals(product4.listOfProducts)
    }
}
