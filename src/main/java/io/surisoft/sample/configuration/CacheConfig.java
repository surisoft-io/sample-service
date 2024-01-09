package io.surisoft.sample.configuration;

import io.surisoft.sample.schema.Item;
import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Value("${cache.name}")
    private String cacheName;

    @Bean
    public Cache<String, Item> itemCache() {
        return new Cache2kBuilder<String, Item>(){}
                .name(cacheName + hashCode())
                .eternal(true)
                .entryCapacity(10000)
                .storeByReference(true)
                .build();
    }
}