package io.asia.store.flyweight.generic.strategy.file.impl;

import io.asia.store.domain.dao.Product;
import io.asia.store.flyweight.domain.FileType;
import io.asia.store.flyweight.generic.strategy.file.GeneratorStrategy;
import io.asia.store.flyweight.generic.strategy.file.exception.FileGenerateException;
import io.asia.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class DOCGeneratorGenericImpl implements GeneratorStrategy {
    private final ProductRepository productRepository;

    @Override
    public byte[] generatorFile() throws FileGenerateException {

        try {
            XWPFDocument document = new XWPFDocument();
            XWPFTable table = document.createTable();
            XWPFTableRow row = table.getRow(0);
            row.getCell(0).setText("id");
            row.addNewTableCell().setText("name");
            row.addNewTableCell().setText("description");
            row.addNewTableCell().setText("price");
            for (Product product : productRepository.findAll()) {
                row = table.createRow();
                row.getCell(0).setText(product.getId().toString());
                row.getCell(1).setText(product.getName());
                row.getCell(2).setText(product.getDescription());
                row.getCell(3).setText(product.getPrice().toString());
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        throw new FileGenerateException("DOC can not generated");
    }

    @Override
    public FileType getType() {
        return FileType.DOC;
    }
}
