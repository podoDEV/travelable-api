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
import world.podo.travelable.domain.country.TravelBanFetchService;
import world.podo.travelable.domain.country.TravelBanFetchValue;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PublicApiTravelBanFetchService implements TravelBanFetchService {
    private final RestTemplate publicApiRestTemplate;
    private final String publicApiHost;
    private final String publicApiTravelBanListPath;

    public PublicApiTravelBanFetchService(
            @Qualifier("publicApiRestTemplate") RestTemplate publicApiRestTemplate,
            @Value("${public.api.host}") String publicApiHost,
            @Value("${public.api.path.travel-ban-list}") String publicApiTravelBanListPath
    ) {
        this.publicApiRestTemplate = publicApiRestTemplate;
        this.publicApiHost = publicApiHost;
        this.publicApiTravelBanListPath = publicApiTravelBanListPath;
    }

    @Override
    public List<TravelBanFetchValue> fetch() {
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
            log.error("Failed to get special warning data. statusCode:" + responseEntity.getStatusCode());
            throw new CountryApiFailedException("Failed to get special warning data. statusCode:" + responseEntity.getStatusCode());
        }
        return this.resolve(responseEntity.getBody());
    }

    private List<TravelBanFetchValue> resolve(Map resultMap) {
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
