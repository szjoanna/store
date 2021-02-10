package io.asia.store.service.impl

import io.asia.store.domain.dao.Category
import io.asia.store.repository.CategoryRepository
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

    def 'test getById category'() {
        given:
        repository.findById(1) >> Optional.of(new Category())

        when:
        def result = categoryService.getById(1)

        then:
        result != null
    }

    def 'test save product'() {
        given:
        def category = new Category(name: "a",)
        repository.save(_ as Category) >> new Category()

        when:
        def result = categoryService.saveCategory(category)

        then:
        result != null
    }

    def 'test update product'() {
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
}
