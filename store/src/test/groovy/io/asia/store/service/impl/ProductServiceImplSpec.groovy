package io.asia.store.service.impl

import io.asia.store.domain.dao.Category
import io.asia.store.domain.dao.Product
import io.asia.store.domain.dao.Role
import io.asia.store.domain.dao.User
import io.asia.store.repository.ProductRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

import javax.persistence.EntityNotFoundException

class ProductServiceImplSpec extends Specification {
    def productService
    def repository = Mock(ProductRepository)
    def categoryService = Mock(CategoryServiceImpl)
    def userService = Mock(UserServiceImpl)


    def setup() {
        productService = new ProductServiceImpl(repository, categoryService, userService)
    }

    def 'test remove product by id'() {
        given:
        repository.findById(1) >> Optional.of(new Product())

        when:
        productService.removeById(1)

        then:
        1 * repository.save(_)
    }

    def 'should not remove product where product not in DB'() {
        given:
        repository.findById(1) >> Optional.empty()

        when:
        productService.removeById(1)

        then:
        thrown EntityNotFoundException
    }

    def 'test save product'() {
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
                .imageUrl("image.png")
                .deleted(false)
                .build()
        repository.save(_ as Product) >> product
        categoryService.getById(1) >> new Category()
        MultipartFile multipartFile = Mock()
        multipartFile.getInputStream() >> GroovyMock(InputStream)

        when:
        def result = productService.saveProduct(product, 1, multipartFile)

        then:
        result != null
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

    def 'should not update product where product not in DB'() {
        given:
        repository.findById(1) >> Optional.empty()

        when:
        productService.update(new Product(), 1, 1)

        then:
        thrown EntityNotFoundException
    }

    def 'test getProductPageWhenUserHasNotRoleAdmin'() {
        given:
        Role role = Role.builder()
                .id(1L)
                .name("ROLE_USER")
                .build()
        User user = User.builder()
                .id(1L)
                .firstName("a")
                .secondName("b")
                .email("a@a")
                .password("abc")
                .version(1L)
                .roles(Arrays.asList(role))
                .build()
        userService.getCurrentUser() >> user
        repository.findByMainAndDeleted(true, false, PageRequest.of(1,3)) >> Page.empty()

        when:
        def result = productService.getPage(true, PageRequest.of(1, 3))

        then:
        result != null
    }

    def 'test getProductPageWhenUserHasRoleAdmin'() {
        given:
        Role role = Role.builder()
                .id(1L)
                .name("ROLE_USER")
                .build()
        List<Role> roles = new ArrayList<>()
        roles.add(role)
        User user = User.builder()
                .id(1L)
                .firstName("a")
                .secondName("b")
                .email("a@a")
                .password("abc")
                .version(1L)
                .roles(roles)
                .build()
        userService.getCurrentUser() >> user
        repository.findAll(PageRequest.of(1,3)) >> Page.empty()

        when:
        def result = productService.getPage(true, PageRequest.of(1, 3))

        then:
        result == null
    }

    def 'should findByMain product'() {
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
                .imageUrl("image.png")
                .deleted(false)
                .build()
        repository.save(_ as Product) >> product
        repository.findByMainAndDeleted(true, false) >> Collections.singletonList(product)

        when:
        def result = productService.findByMain(true)

        then:
        result == Collections.singletonList(product)
    }

    def 'should autoComplete product'() {
        given:
        def productName = "test_name"
        def value = "st"
        repository.autoComplete(value) >> Collections.singletonList(productName)

        when:
        def result = productService.autoComplete(value)

        then:
        result == Collections.singletonList(productName)
    }
}
