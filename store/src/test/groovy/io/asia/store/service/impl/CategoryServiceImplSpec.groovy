package io.asia.store.service.impl

import io.asia.store.domain.dao.Category
import io.asia.store.repository.CategoryRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

import javax.persistence.EntityNotFoundException

class CategoryServiceImplSpec extends Specification {
    def categoryService
    def repository = Mock(CategoryRepository)

    def setup() {
        categoryService = new CategoryServiceImpl(repository)
    }

    def 'test remove category by id'() {
        given:
        repository.deleteById(1) >> null

        when:
        categoryService.removeById(1)

        then:
        1 * repository.deleteById(1)
    }

    def 'should not findById category'() {
        given:
        repository.findById(1) >> Optional.empty()

        when:
        categoryService.getById(1)

        then:
        thrown EntityNotFoundException
    }

    def 'test findById category'() {
        given:
        repository.findById(1) >> Optional.of(new Category())

        when:
        def result = categoryService.getById(1)

        then:
        result != null
    }

    def 'test save category'() {
        given:
        def category = new Category(name: "a",)
        repository.save(_ as Category) >> new Category()

        when:
        def result = categoryService.saveCategory(category)

        then:
        result != null
    }

    def 'test update category'() {
        given:
        repository.findById(1) >> Optional.of(new Category())

        when:
        categoryService.update(new Category(), 1)

        then:
        1 * repository.save(_)
    }

    def 'should not update category'() {
        given:
        repository.findById(1) >> Optional.empty()

        when:
        categoryService.update(new Category(), 1)

        then:
        thrown EntityNotFoundException
    }

    def 'test get category page'() {
        given:
        repository.findAll(PageRequest.of(1,3)) >> Page.empty()

        when:
        def result = categoryService.getPage(PageRequest.of(1,3))

        then:
        result != null
    }

    def 'test find all category'() {
        given:
        def category = Category.builder()
                .id(1L)
                .name("a")
                .version(1L)
                .createdDate(null)
                .lastModifiedDate(null)
                .build()
        repository.findAll() >> Collections.singletonList(category)

        when:
        def result = categoryService.findAll()

        then:
        result != null
    }
}
