package io.asia.store.controller;

import io.asia.store.domain.dto.ProductDto;
import io.asia.store.mapper.ProductMapper;
import io.asia.store.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable Long id) {
        return productMapper.toDto(productService.findById(id));
    }

    @PostMapping
    public ProductDto saveProduct(@RequestBody ProductDto productDto) {
        return productMapper.toDto(productService.saveProduct(productMapper.toDao(productDto), productDto.getCategoryId()));
    }

    @GetMapping
    public Page<ProductDto> getPageProduct(@RequestParam int page, @RequestParam int size, @RequestParam boolean main) {
        return productService.getPage(main, PageRequest.of(page, size)).map(productMapper:: toDto);
    }

    @DeleteMapping("/{id}")
    public void removeProductById(@PathVariable Long id) {
        productService.removeById(id);
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@RequestBody ProductDto productDto, @PathVariable Long id) {
        return productMapper.toDto(productService.update(productMapper.toDao(productDto), id, productDto.getCategoryId()));
    }
}
