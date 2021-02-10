package io.asia.store.flyweight.strategy;

import io.asia.store.flyweight.domain.FileType;
import lombok.Getter;

public abstract class GeneratorStrategy {
    @Getter
    private FileType fileType;

    protected GeneratorStrategy(FileType fileType) {
        this.fileType = fileType;
    }

    public abstract byte[] generatedFile();
}
