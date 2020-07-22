package world.podo.travelable.infrastructure.spring;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(CacheName.names());
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
