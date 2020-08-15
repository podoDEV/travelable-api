package world.podo.travelable.infrastructure.public_api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import world.podo.travelable.domain.country.SpecialWarningFetchService;
import world.podo.travelable.domain.country.SpecialWarningFetchValue;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service(PublicApiSpecialWarningFetchService.BEAN_NAME)
public class PublicApiSpecialWarningFetchService implements SpecialWarningFetchService {
    public static final String BEAN_NAME = "publicApiSpecialWarningFetchService";

    private final RestTemplate publicApiRestTemplate;
    private final String publicApiHost;
    private final String publicApiSpecialWarningListPath;

    public PublicApiSpecialWarningFetchService(
            @Qualifier("publicApiRestTemplate") RestTemplate publicApiRestTemplate,
            @Value("${public.api.host}") String publicApiHost,
            @Value("${public.api.path.special-warning-list}") String publicApiSpecialWarningListPath
    ) {
        this.publicApiRestTemplate = publicApiRestTemplate;
        this.publicApiHost = publicApiHost;
        this.publicApiSpecialWarningListPath = publicApiSpecialWarningListPath;
    }

    @Override
    public List<SpecialWarningFetchValue> fetch() {
        URI requestUrl = UriComponentsBuilder.fromHttpUrl(publicApiHost)
                                             .path(publicApiSpecialWarningListPath)
                                             .queryParams(PublicApiUtils.createQueryParams())
                                             .build(true)
                                             .toUri();
        ResponseEntity<Map> responseEntity = publicApiRestTemplate.exchange(
                new RequestEntity(HttpMethod.GET, requestUrl), Map.class
        );
        if (!responseEntity.getStatusCode()
                           .is2xxSuccessful() || responseEntity.getBody() == null) {
            log.error("Failed to get special warning data. statusCode:" + responseEntity.getStatusCode());
            throw new CountryApiFailedException("Failed to get special warning data. statusCode:" + responseEntity.getStatusCode());
        }
        return this.resolve(responseEntity.getBody());
    }

    private List<SpecialWarningFetchValue> resolve(Map resultMap) {
        Map<String, Object> responseMap = (Map<String, Object>) resultMap.get("response");
        Map<String, Object> bodyMap = (Map<String, Object>) responseMap.get("body");
        Integer totalCount = (int) bodyMap.get("totalCount");
        if (totalCount == 0) {
            return Collections.emptyList();
        }
        Map<String, Object> itemsMap = (Map<String, Object>) bodyMap.get("items");
        List<Map<String, Object>> itemList = (List<Map<String, Object>>) itemsMap.get("item");
        return itemList.stream()
                       .map(this::toSpecialWarningFetchValue)
                       .collect(Collectors.toList());
    }

    private SpecialWarningFetchValue toSpecialWarningFetchValue(Map<String, Object> specialWarningFetchMap) {
        if (specialWarningFetchMap == null) {
            return null;
        }
        return new SpecialWarningFetchValueImpl(
                PublicApiUtils.get(specialWarningFetchMap, SpecialWarningFetchValueImpl.FieldName.ID),
                PublicApiUtils.get(specialWarningFetchMap, SpecialWarningFetchValueImpl.FieldName.SP_LIMIT),
                PublicApiUtils.get(specialWarningFetchMap, SpecialWarningFetchValueImpl.FieldName.SP_LIMIT_PARTIAL),
                PublicApiUtils.get(specialWarningFetchMap, SpecialWarningFetchValueImpl.FieldName.SP_LIMIT_NOTE),
                PublicApiUtils.get(specialWarningFetchMap, SpecialWarningFetchValueImpl.FieldName.SP_BAN_YNA),
                PublicApiUtils.get(specialWarningFetchMap, SpecialWarningFetchValueImpl.FieldName.SP_BAN_PARTIAL),
                PublicApiUtils.get(specialWarningFetchMap, SpecialWarningFetchValueImpl.FieldName.SP_BAN_NOTE)
        );
    }
}
