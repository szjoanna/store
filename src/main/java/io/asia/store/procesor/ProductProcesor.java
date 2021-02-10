package io.asia.store.procesor;

import io.asia.store.domain.dao.Product;
import io.asia.store.procesor.model.ProductCSV;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ProductProcesor implements ItemProcessor<ProductCSV, Product> {

    @Override
    public Product process(ProductCSV productCSV) throws Exception {
        return Product.builder()
                .name(productCSV.getName())
                .description(productCSV.getDescription())
                .price(productCSV.getPrice())
                .build();
    }
}
