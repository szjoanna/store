package io.asia.store.flyweight.generic.strategy.file.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import io.asia.store.flyweight.domain.FileType;
import io.asia.store.flyweight.generic.strategy.file.GeneratorStrategy;
import io.asia.store.flyweight.generic.strategy.file.exception.FileGenerateException;
import io.asia.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

@RequiredArgsConstructor
@Component
@Slf4j
public class PDFGeneratorGenericImpl implements GeneratorStrategy {
    private final ProductRepository productRepository;

    @Override
    public byte[] generatorFile() throws FileGenerateException {
        Document document = new Document(PageSize.A4.rotate());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();
            PdfPTable table = new PdfPTable(4);
            table.addCell("id");
            table.addCell("name");
            table.addCell("description");
            table.addCell("price");
            productRepository.findAll().forEach(product -> {
                table.addCell(product.getId().toString());
                table.addCell(product.getName());
                table.addCell(product.getDescription());
                table.addCell(String.valueOf(product.getPrice()));
            });
            document.add(table);
            document.close();
            return outputStream.toByteArray();
        } catch (DocumentException e) {
            log.error(e.getMessage(), e);
        }
        throw new FileGenerateException("Excel can not generated");
    }

    @Override
    public FileType getType() {
        return FileType.PDF;
    }
}
