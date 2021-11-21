package io.asia.store.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.asia.store.domain.dto.ProductDto;
import io.asia.store.mapper.ProductMapper;
import io.asia.store.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Validated
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final ObjectMapper objectMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findProductById(@PathVariable Long id) {
        log.info("get product from controler {}", id);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES))
                .body(productMapper.toDto(productService.findById(id)));
    }

    @GetMapping("/main")
    public List<ProductDto> findProductsByMain(@RequestParam boolean isMain) {
        return productMapper.listProductsToDto(productService.findByMain(isMain));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ProductDto saveProduct(@RequestPart @Valid ProductDto productDto, @RequestPart MultipartFile file) throws JsonProcessingException {
        return productMapper.toDto(productService.saveProduct(productMapper.toDao(productDto), productDto.getCategoryId(), file));
    }

    @GetMapping
    public Page<ProductDto> getPageProduct(@RequestParam int page, @RequestParam int size, @RequestParam boolean main) {
        return productService.getPage(main, PageRequest.of(page, size)).map(productMapper::toDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void removeProductById(@PathVariable Long id) {
        productService.removeById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductDto updateProduct(@RequestBody @Valid ProductDto productDto, @PathVariable Long id) {
        return productMapper.toDto(productService.update(productMapper.toDao(productDto), id, productDto.getCategoryId()));
    }

    @GetMapping("/autocomplete")
    public List<String> autoComplete(@RequestParam String value) {
        return productService.autoComplete(value);
    }
}
