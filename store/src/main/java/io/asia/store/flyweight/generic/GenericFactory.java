package io.asia.store.flyweight.generic;

import io.asia.store.flyweight.generic.strategy.GenericStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class GenericFactory <K, V extends GenericStrategy<K>> {
    private final List<V> strategyList;
    private Map<K, V> strategyMap;

    @PostConstruct
    void init () {
        this.strategyMap = strategyList.stream().collect(Collectors.toMap(GenericStrategy::getType, genericStrategy -> genericStrategy));
    }

    public V getByKey(K key){
        return strategyMap.get(key);
    }
}
