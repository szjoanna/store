package io.asia.store.controller;

import io.asia.store.domain.dto.BasketDto;
import io.asia.store.mapper.BasketMapper;
import io.asia.store.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/baskets")
@RequiredArgsConstructor
public class BasketController {
    private final BasketService basketService;
    private final BasketMapper basketMapper;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<BasketDto> getBasketByCurrentUser() {
        return basketMapper.listBasketsToDto(basketService.getBasketByCurrentUser());
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public BasketDto addProductToBasket(@RequestParam Long idProduct,@RequestParam Double quantity) {
        return basketMapper.toDto(basketService.addProductToBasket(idProduct, quantity));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public void deleteProductFromBasket(@PathVariable Long id) {
        basketService.deleteProductFromBasket(id);
    }

    @DeleteMapping()
    @PreAuthorize("isAuthenticated()")
    public void  deleteAllCurrentUserProducts() {
        basketService.deleteAllCurrentUserProducts();
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public BasketDto updateProductFromBasket(@RequestParam Double quantity, @PathVariable Long id) {
        return basketMapper.toDto(basketService.updateProductFromBasket(id, quantity ));
    }
}
