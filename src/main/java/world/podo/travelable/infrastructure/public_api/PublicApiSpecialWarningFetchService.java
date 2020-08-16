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
import world.podo.travelable.domain.country.SpecialWarningFetchService;
import world.podo.travelable.domain.country.SpecialWarningFetchValue;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service(PublicApiSpecialWarningFetchService.BEAN_NAME)
public class PublicApiSpecialWarningFetchService implements SpecialWarningFetchService {
    public static final String BEAN_NAME = "publicApiSpecialWarningFetchService";

    private final RestTemplate publicApiRestTemplate;
    private final String publicApiHost;
    private final String publicApiSpecialWarningListPath;
    private final String publicApiSpecialWarningListInfo;

    public PublicApiSpecialWarningFetchService(
            @Qualifier("publicApiRestTemplate") RestTemplate publicApiRestTemplate,
            @Value("${public.api.host}") String publicApiHost,
            @Value("${public.api.path.special-warning-list}") String publicApiSpecialWarningListPath,
            @Value("${public.api.path.special-warning-info}") String publicApiSpecialWarningListInfo
    ) {
        this.publicApiRestTemplate = publicApiRestTemplate;
        this.publicApiHost = publicApiHost;
        this.publicApiSpecialWarningListPath = publicApiSpecialWarningListPath;
        this.publicApiSpecialWarningListInfo = publicApiSpecialWarningListInfo;
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
        return this.parseList(responseEntity.getBody());
    }

    @Override
    public Optional<SpecialWarningFetchValue> fetchOne(String providerCountryId) {
        Assert.notNull(providerCountryId, "'providerCountryId' must not be null");

        URI requestUrl = UriComponentsBuilder.fromHttpUrl(publicApiHost)
                                             .path(publicApiSpecialWarningListInfo)
                                             .queryParams(PublicApiUtils.createQueryParams())
                                             .queryParam("id", providerCountryId)
                                             .build(true)
                                             .toUri();
        ResponseEntity<Map> responseEntity = publicApiRestTemplate.exchange(
                new RequestEntity(HttpMethod.GET, requestUrl), Map.class
        );
        if (!responseEntity.getStatusCode()
                           .is2xxSuccessful() || responseEntity.getBody() == null) {
            log.error("Failed to get special warning info data. statusCode:" + responseEntity.getStatusCode());
            throw new CountryApiFailedException("Failed to get special warning info data. statusCode:" + responseEntity.getStatusCode());
        }
        return Optional.ofNullable(
                this.parseInfo(responseEntity.getBody())
        );
    }

    private List<SpecialWarningFetchValue> parseList(Map resultMap) {
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

    private SpecialWarningFetchValue parseInfo(Map resultMap) {
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
        return toSpecialWarningFetchValue(itemMap);
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
