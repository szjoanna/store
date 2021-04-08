package io.asia.store.flyweight.generic.strategy.file.impl;

import com.opencsv.CSVWriter;
import io.asia.store.flyweight.domain.FileType;
import io.asia.store.flyweight.generic.strategy.file.GeneratorStrategy;
import io.asia.store.flyweight.generic.strategy.file.exception.FileGenerateException;
import io.asia.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

@RequiredArgsConstructor
@Component
@Slf4j
public class CSVGeneratorGenericImpl implements GeneratorStrategy {
    private final ProductRepository productRepository;

    @Override
    public byte[] generatorFile() throws FileGenerateException {
        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(stringWriter);
        csvWriter.writeNext(new String[]{"id", "name", "description", "price"});
        productRepository.findAll().forEach(
                product -> csvWriter.writeNext(new String[]{product.getId().toString(), product.getName(), product.getDescription(), product.getPrice().toString()})
        );
        return stringWriter.toString().getBytes();
    }

    @Override
    public FileType getType() {
        return FileType.CSV;
    }
}
