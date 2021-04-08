package io.asia.store.procesor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCSV {
    private Long id;
    private String name;
    private String description;
    private Double price;
}
