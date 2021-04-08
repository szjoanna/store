package io.asia.store.flyweight.generic.strategy.file;

import io.asia.store.flyweight.domain.FileType;
import io.asia.store.flyweight.generic.strategy.GenericStrategy;
import io.asia.store.flyweight.generic.strategy.file.exception.FileGenerateException;

public interface GeneratorStrategy extends GenericStrategy <FileType> {
    byte[] generatorFile() throws FileGenerateException;
}
