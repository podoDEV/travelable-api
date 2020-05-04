package world.podo.emergency.infrastructure.public_api;

import lombok.experimental.UtilityClass;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import world.podo.emergency.domain.FieldNameSupport;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@UtilityClass
class PublicApiUtils {
    static MultiValueMap<String, String> createQueryParams() {
        return createQueryParams(1, 200);
    }

    static MultiValueMap<String, String> createQueryParams(int page, int size) {
        MultiValueMap<String, String> queryParameterMap = new LinkedMultiValueMap<>();
        queryParameterMap.set("_type", "json");
        queryParameterMap.set("pageNo", String.valueOf(page));
        queryParameterMap.set("numOfRows", String.valueOf(size));
        return queryParameterMap;
    }

    static String getOrDefault(Map<String, Object> map, String key, Object defaultValue) {
        return Optional.ofNullable(map.getOrDefault(key, defaultValue))
                       .filter(Objects::nonNull)
                       .map(String::valueOf)
                       .map(String::trim)
                       .orElse(null);
    }

    static String get(Map<String, Object> map, FieldNameSupport fieldNameSupporter) {
        return get(map, fieldNameSupporter.getFieldName());
    }

    static String get(Map<String, Object> map, String key) {
        return getOrDefault(map, key, null);
    }
}
