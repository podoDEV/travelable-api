package world.podo.emergency.infrastructure.public_api;

import lombok.experimental.UtilityClass;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@UtilityClass
class PublicApiUtils {
    private static final MultiValueMap<String, String> DEFAULT_QUERY_PARAMETER_MAP;

    static {
        DEFAULT_QUERY_PARAMETER_MAP = new LinkedMultiValueMap<>();
        DEFAULT_QUERY_PARAMETER_MAP.set("_type", "json");
        DEFAULT_QUERY_PARAMETER_MAP.set("pageNo", "1");
        DEFAULT_QUERY_PARAMETER_MAP.set("numOfRows", "200");
    }

    static MultiValueMap<String, String> createQueryParams() {
        return DEFAULT_QUERY_PARAMETER_MAP;
    }

    static String getOrDefault(Map<String, Object> map, String key, Object defaultValue) {
        return Optional.ofNullable(map.getOrDefault(key, defaultValue))
                       .filter(Objects::nonNull)
                       .map(String::valueOf)
                       .orElse(null);
    }

    static String get(Map<String, Object> map, String key) {
        return getOrDefault(map, key, null);
    }
}
