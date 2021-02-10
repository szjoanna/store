package io.asia.store.config;

import com.hazelcast.config.*;
import io.asia.store.domain.dao.Product;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class HazelcastConfig {

    @Bean
    Config configHazelcast() {
        Config config = new Config()
                .setInstanceName("hazelcast-instance")
                .addMapConfig(new MapConfig()
                        .setName("product")
                        .setEvictionConfig(new EvictionConfig()
                                .setEvictionPolicy(EvictionPolicy.LFU)
                                .setSize(1111)
                                .setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE))
                        .setTimeToLiveSeconds(60 * 60 * 24));
        config.getSerializationConfig().addDataSerializableFactory(1, id -> id==1 ? new Product() : null);
        return config;
    }
}
