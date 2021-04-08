package io.asia.store.flyweight.generic.strategy.file.impl;

import io.asia.store.domain.dao.Product;
import io.asia.store.flyweight.domain.FileType;
import io.asia.store.flyweight.generic.strategy.file.GeneratorStrategy;
import io.asia.store.flyweight.generic.strategy.file.exception.FileGenerateException;
import io.asia.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class XLSGeneratorGenericImpl implements GeneratorStrategy {
    private final ProductRepository productRepository;

    @Override
    public byte[] generatorFile() throws FileGenerateException {
        try {
            Workbook workbook = WorkbookFactory.create(true);

            Sheet sheet = workbook.createSheet("Report");
            int rowCount = 0;
            Row row = sheet.createRow(rowCount);
            row.createCell(0).setCellValue("id");
            row.createCell(1).setCellValue("name");
            row.createCell(2).setCellValue("description");
            row.createCell(3).setCellValue("price");
            for (Product product : productRepository.findAll()) {
                rowCount++;
                row = sheet.createRow(rowCount);
                row.createCell(0).setCellValue(product.getId());
                row.createCell(1).setCellValue(product.getName());
                row.createCell(2).setCellValue(product.getDescription());
                row.createCell(3).setCellValue(product.getPrice());
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        throw new FileGenerateException("Excel can not generated");
    }

    @Override
    public FileType getType() {
        return FileType.XLS;
    }
}
