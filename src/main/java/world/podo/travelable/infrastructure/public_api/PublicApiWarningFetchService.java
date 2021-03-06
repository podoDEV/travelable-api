package world.podo.travelable.infrastructure.public_api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import world.podo.travelable.domain.country.WarningFetchService;
import world.podo.travelable.domain.country.WarningFetchValue;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service(PublicApiWarningFetchService.BEAN_NAME)
public class PublicApiWarningFetchService implements WarningFetchService {
    public static final String BEAN_NAME = "publicApiWarningFetchService";

    private final RestTemplate publicApiRestTemplate;
    private final String publicApiHost;
    private final String publicApiWarningListPath;
    private final String publicApiWarningInfoPath;

    public PublicApiWarningFetchService(
            @Qualifier("publicApiRestTemplate") RestTemplate publicApiRestTemplate,
            @Value("${public.api.host}") String publicApiHost,
            @Value("${public.api.path.warning-list}") String publicApiWarningListPath,
            @Value("${public.api.path.warning-info}") String publicApiWarningInfoPath
    ) {
        this.publicApiRestTemplate = publicApiRestTemplate;
        this.publicApiHost = publicApiHost;
        this.publicApiWarningListPath = publicApiWarningListPath;
        this.publicApiWarningInfoPath = publicApiWarningInfoPath;
    }

    @Override
    public List<WarningFetchValue> fetch() {
        URI requestUrl = UriComponentsBuilder.fromHttpUrl(publicApiHost)
                                             .path(publicApiWarningListPath)
                                             .queryParams(PublicApiUtils.createQueryParams())
                                             .build(true)
                                             .toUri();
        ResponseEntity<Map> responseEntity = publicApiRestTemplate.exchange(
                new RequestEntity(HttpMethod.GET, requestUrl), Map.class
        );
        if (!responseEntity.getStatusCode()
                           .is2xxSuccessful() || responseEntity.getBody() == null) {
            log.error("Failed to get warning data. statusCode:" + responseEntity.getStatusCode());
            throw new CountryApiFailedException("Failed to get warning data. statusCode:" + responseEntity.getStatusCode());
        }
        return this.resolve(responseEntity.getBody());
    }

    @Override
    public Optional<WarningFetchValue> fetchOne(String providerCountryId) {
        Assert.notNull(providerCountryId, "'providerCountryId' must not be null");

        URI requestUrl = UriComponentsBuilder.fromHttpUrl(publicApiHost)
                                             .path(publicApiWarningInfoPath)
                                             .queryParams(PublicApiUtils.createQueryParams())
                                             .queryParam("id", providerCountryId)
                                             .build(true)
                                             .toUri();
        ResponseEntity<Map> responseEntity = publicApiRestTemplate.exchange(
                new RequestEntity(HttpMethod.GET, requestUrl), Map.class
        );
        if (!responseEntity.getStatusCode()
                           .is2xxSuccessful() || responseEntity.getBody() == null) {
            log.error("Failed to get warning info data. statusCode:" + responseEntity.getStatusCode());
            throw new CountryApiFailedException("Failed to get warning info data. statusCode:" + responseEntity.getStatusCode());
        }
        return Optional.ofNullable(
                this.resolveInfo(responseEntity.getBody())
        );
    }

    private List<WarningFetchValue> resolve(Map resultMap) {
        Map<String, Object> responseMap = (Map<String, Object>) resultMap.get("response");
        Map<String, Object> bodyMap = (Map<String, Object>) responseMap.get("body");
        Integer totalCount = (int) bodyMap.get("totalCount");
        if (totalCount == 0) {
            return Collections.emptyList();
        }
        Map<String, Object> itemsMap = (Map<String, Object>) bodyMap.get("items");
        List<Map<String, Object>> itemList = (List<Map<String, Object>>) itemsMap.get("item");
        return itemList.stream()
                       .map(this::toWarningFetchValue)
                       .collect(Collectors.toList());
    }

    private WarningFetchValue resolveInfo(Map resultMap) {
        Map<String, Object> responseMap = (Map<String, Object>) resultMap.get("response");
        Map<String, Object> headerMap = (Map<String, Object>) responseMap.get("header");
        String resultCode = (String) headerMap.get("resultCode");
        if (!"00".equals(resultCode)) {
            log.warn("ResultCode must be '00'. resultMap:" + resultMap);
            return null;
        }
        Object body = responseMap.get("body");
        if (!(body instanceof Map)) {
            log.warn("Body's type must be Map. resultMap:" + resultMap);
            return null;
        }
        Map<String, Object> bodyMap = (Map<String, Object>) body;
        if (bodyMap.isEmpty()) {
            log.warn("BodyMap must not be empty. resultMap: " + resultMap);
            return null;
        }
        Map<String, Object> itemMap = (Map<String, Object>) bodyMap.get("item");
        return toWarningFetchValue(itemMap);
    }

    private WarningFetchValue toWarningFetchValue(Map<String, Object> warningFetchMap) {
        if (warningFetchMap == null) {
            return null;
        }
        return new WarningFetchValueImpl(
                PublicApiUtils.get(warningFetchMap, WarningFetchValueImpl.FieldName.ID),
                PublicApiUtils.get(warningFetchMap, WarningFetchValueImpl.FieldName.ATTENTION),
                PublicApiUtils.get(warningFetchMap, WarningFetchValueImpl.FieldName.ATTENTION_PARTIAL),
                PublicApiUtils.get(warningFetchMap, WarningFetchValueImpl.FieldName.ATTENTION_NOTE),
                PublicApiUtils.get(warningFetchMap, WarningFetchValueImpl.FieldName.CONTROL),
                PublicApiUtils.get(warningFetchMap, WarningFetchValueImpl.FieldName.CONTROL_PARTIAL),
                PublicApiUtils.get(warningFetchMap, WarningFetchValueImpl.FieldName.CONTROL_NOTE),
                PublicApiUtils.get(warningFetchMap, WarningFetchValueImpl.FieldName.LIMITA),
                PublicApiUtils.get(warningFetchMap, WarningFetchValueImpl.FieldName.LIMITA_PARTIAL),
                PublicApiUtils.get(warningFetchMap, WarningFetchValueImpl.FieldName.LIMITA_NOTE)
        );
    }
}
