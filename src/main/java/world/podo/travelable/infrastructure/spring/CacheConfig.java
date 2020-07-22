package world.podo.travelable.infrastructure.spring;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(CacheName.names()) {
            @Override
            protected Cache createConcurrentMapCache(String name) {
                return new ConcurrentMapCache(
                        name,
                        CacheBuilder.newBuilder()
                                    .expireAfterWrite(2L, TimeUnit.DAYS)
                                    .build()
                                    .asMap(),
                        false);
            }
        };
    }

    public enum CacheName {
        COVID;

        public static String[] names() {
            return Arrays.stream(values())
                         .map(Enum::name)
                         .toArray(String[]::new);
        }
    }
}
