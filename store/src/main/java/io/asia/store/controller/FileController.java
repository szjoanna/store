package io.asia.store.controller;

import io.asia.store.flyweight.GeneratorFactory;
import io.asia.store.flyweight.domain.FileType;
import io.asia.store.flyweight.generic.GenericFactory;
import io.asia.store.flyweight.generic.strategy.file.GeneratorStrategy;
import io.asia.store.flyweight.generic.strategy.file.exception.FileGenerateException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {
    private final GeneratorFactory generatorFactory;
    private final GenericFactory <FileType, GeneratorStrategy> genericFactory;

    @GetMapping
    void test (@RequestParam FileType fileType) {
        generatorFactory.getValueByFileType(fileType).generatedFile();
    }

    @GetMapping("/generic")
    ResponseEntity<byte[]> testGeneric (@RequestParam FileType fileType) throws FileGenerateException {
        byte[] file = genericFactory.getByKey(fileType).generatorFile();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        httpHeaders.set(HttpHeaders.CONTENT_LENGTH, Integer.toString(file.length));
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=report." + fileType.toString().toLowerCase());
        return ResponseEntity.ok().headers(httpHeaders).body(file);
    }
}
