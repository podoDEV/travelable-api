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
import world.podo.travelable.domain.country.TravelBanFetchService;
import world.podo.travelable.domain.country.TravelBanFetchValue;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service(PublicApiTravelBanFetchService.BEAN_NAME)
public class PublicApiTravelBanFetchService implements TravelBanFetchService {
    public static final String BEAN_NAME = "publicApiTravelBanFetchService";

    private final RestTemplate publicApiRestTemplate;
    private final String publicApiHost;
    private final String publicApiTravelBanListPath;
    private final String publicApiTravelBanInfoPath;

    public PublicApiTravelBanFetchService(
            @Qualifier("publicApiRestTemplate") RestTemplate publicApiRestTemplate,
            @Value("${public.api.host}") String publicApiHost,
            @Value("${public.api.path.travel-ban-list}") String publicApiTravelBanListPath,
            @Value("${public.api.path.travel-ban-info}") String publicApiTravelBanInfoPath
    ) {
        this.publicApiRestTemplate = publicApiRestTemplate;
        this.publicApiHost = publicApiHost;
        this.publicApiTravelBanListPath = publicApiTravelBanListPath;
        this.publicApiTravelBanInfoPath = publicApiTravelBanInfoPath;
    }

    @Override
    public List<TravelBanFetchValue> fetchList() {
        URI requestUrl = UriComponentsBuilder.fromHttpUrl(publicApiHost)
                                             .path(publicApiTravelBanListPath)
                                             .queryParams(PublicApiUtils.createQueryParams())
                                             .build(true)
                                             .toUri();
        ResponseEntity<Map> responseEntity = publicApiRestTemplate.exchange(
                new RequestEntity(HttpMethod.GET, requestUrl), Map.class
        );
        if (!responseEntity.getStatusCode()
                           .is2xxSuccessful() || responseEntity.getBody() == null) {
            log.error("Failed to get travel ban list data. statusCode:" + responseEntity.getStatusCode());
            throw new CountryApiFailedException("Failed to get travel ban list data. statusCode:" + responseEntity.getStatusCode());
        }
        return this.resolveList(responseEntity.getBody());
    }

    @Override
    public Optional<TravelBanFetchValue> fetchOne(String providerCountryId) {
        Assert.notNull(providerCountryId, "'providerCountryId' must not be null");

        URI requestUrl = UriComponentsBuilder.fromHttpUrl(publicApiHost)
                                             .path(publicApiTravelBanInfoPath)
                                             .queryParams(PublicApiUtils.createQueryParams())
                                             .queryParam("id", providerCountryId)
                                             .build(true)
                                             .toUri();
        ResponseEntity<Map> responseEntity = publicApiRestTemplate.exchange(
                new RequestEntity(HttpMethod.GET, requestUrl), Map.class
        );
        if (!responseEntity.getStatusCode()
                           .is2xxSuccessful() || responseEntity.getBody() == null) {
            log.error("Failed to get travel ban info data. statusCode:" + responseEntity.getStatusCode());
            throw new CountryApiFailedException("Failed to get travel ban info data. statusCode:" + responseEntity.getStatusCode());
        }
        return Optional.ofNullable(
                this.resolveInfo(responseEntity.getBody())
        );
    }

    private List<TravelBanFetchValue> resolveList(Map resultMap) {
        Map<String, Object> responseMap = (Map<String, Object>) resultMap.get("response");
        Map<String, Object> bodyMap = (Map<String, Object>) responseMap.get("body");
        Integer totalCount = (int) bodyMap.get("totalCount");
        if (totalCount == 0) {
            return Collections.emptyList();
        }
        Map<String, Object> itemsMap = (Map<String, Object>) bodyMap.get("items");
        List<Map<String, Object>> itemList = (List<Map<String, Object>>) itemsMap.get("item");
        return itemList.stream()
                       .map(this::toTravelBanFetchValue)
                       .collect(Collectors.toList());
    }

    private TravelBanFetchValue resolveInfo(Map resultMap) {
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
        return toTravelBanFetchValue(itemMap);
    }

    private TravelBanFetchValue toTravelBanFetchValue(Map<String, Object> travelBanFetchMap) {
        if (travelBanFetchMap == null) {
            return null;
        }
        return new TravelBanFetchValueImpl(
                PublicApiUtils.get(travelBanFetchMap, TravelBanFetchValueImpl.FieldName.ID),
                PublicApiUtils.get(travelBanFetchMap, TravelBanFetchValueImpl.FieldName.BAN),
                PublicApiUtils.get(travelBanFetchMap, TravelBanFetchValueImpl.FieldName.BAN_PARTIAL),
                PublicApiUtils.get(travelBanFetchMap, TravelBanFetchValueImpl.FieldName.BAN_NOTE)
        );
    }
}
