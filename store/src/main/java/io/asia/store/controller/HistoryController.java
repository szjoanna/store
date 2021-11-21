package io.asia.store.controller;

import io.asia.store.domain.dto.ProductDto;
import io.asia.store.domain.dto.UserDto;
import io.asia.store.mapper.HistoryMapper;
import io.asia.store.repository.ProductRepository;
import io.asia.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/history")
public class HistoryController {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final HistoryMapper historyMapper;

    @GetMapping("/user/{idUser}")
    Page<UserDto> getUserHistory(@PathVariable Long idUser, @RequestParam int page, @RequestParam int size) {
        return userRepository.findRevisions(idUser, PageRequest.of(page, size)).map(historyMapper::toUserDto);
    }

    @GetMapping("/product/{idProduct}")
    Page<ProductDto> getProductHistory(@PathVariable Long idProduct, @RequestParam int page, @RequestParam int size) {
        return productRepository.findRevisions(idProduct, PageRequest.of(page, size)).map(historyMapper::toProductDto);
    }
}
