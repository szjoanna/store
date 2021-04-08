package io.asia.store.procesor;

import io.asia.store.domain.dao.Product;
import io.asia.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ProductWriter implements ItemWriter<Product> {
    private final ProductRepository productRepository;

    @Override
    public void write(List<? extends Product> list) throws Exception {
        productRepository.saveAll(list);
    }
}
