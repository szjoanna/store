package io.asia.store.service.impl

import io.asia.store.domain.dao.Category
import io.asia.store.domain.dao.Product
import io.asia.store.repository.ProductRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

import javax.persistence.EntityNotFoundException

class ProductServiceImplSpec extends Specification {
    def productService
    def repository = Mock(ProductRepository)
    def service = Mock(CategoryServiceImpl)


    def setup() {
        productService = new ProductServiceImpl(repository, service)
    }

    def 'test remove product by id'() {
        given:
        repository.deleteById(1) >> null

        when:
        productService.removeById(1)

        then:
        1 * repository.deleteById(1)
    }

    def 'test save product'() {
        given:
        def product = new Product(name: "a", description: "b", price: 2, main: true)
        repository.save(_ as Product) >> new Product()
        service.getById(1) >> new Category()

        when:
        def result = productService.saveProduct(product, 1)

        then:
        result != null
    }

    def 'should not save product'() {
        given:
        def product = new Product(name: "a", description: "b", price: 2, main: true)
        repository.save(_ as Product) >> new Product()
        service.getById(1) >> { throw new EntityNotFoundException() }

        when:
        productService.saveProduct(product, 1)

        then:
        thrown EntityNotFoundException
    }

    def 'should not findById product'() {
        given:
        repository.findById(1) >> Optional.empty()

        when:
        productService.findById(1)

        then:
        thrown EntityNotFoundException
    }

    def 'test findById product'() {
        given:
        repository.findById(1) >> Optional.of(new Product())

        when:
        def result = productService.findById(1)

        then:
        result != null
    }

    def 'test update product'() {
        given:
        repository.findById(1) >> Optional.of(new Product())
        service.getById(1) >> new Category()

        when:
        productService.update(new Product(), 1, 1)

        then:
        1 * repository.save(_)
    }

    def 'should not update product where category not in DB'() {
        given:
        repository.findById(1) >> Optional.of(new Product())
        service.getById(1) >> {throw new EntityNotFoundException()}

        when:
        productService.update(new Product(), 1, 1)

        then:
        thrown EntityNotFoundException
    }

    def 'should not update product where product not in DB'() {
        given:
        repository.findById(1) >> Optional.empty()

        when:
        productService.update(new Product(), 1, 1)

        then:
        thrown EntityNotFoundException
    }

    def 'test getProductPage'() {
        given:
        repository.findByMain(true, PageRequest.of(1,3)) >> Page.empty()

        when:
        def result = productService.getPage(true, PageRequest.of(1,3))

        then:
        result != null
    }

}
