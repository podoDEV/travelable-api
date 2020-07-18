package world.podo.travelable.infrastructure.public_api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import world.podo.travelable.domain.country.CountryFetchService;
import world.podo.travelable.domain.country.CountryFetchValue;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PublicApiCountryFetchService implements CountryFetchService {
    private final RestTemplate publicApiRestTemplate;
    private final String publicApiHost;
    private final String publicApiCountryListPath;

    public PublicApiCountryFetchService(
            @Qualifier("publicApiRestTemplate") RestTemplate publicApiRestTemplate,
            @Value("${public.api.host}") String publicApiHost,
            @Value("${public.api.path.country-basic-list}") String publicApiCountryBasicListPath
    ) {
        this.publicApiRestTemplate = publicApiRestTemplate;
        this.publicApiHost = publicApiHost;
        this.publicApiCountryListPath = publicApiCountryBasicListPath;
    }

    @Override
    public List<CountryFetchValue> fetch() {
        URI requestUrl = UriComponentsBuilder.fromHttpUrl(publicApiHost)
                                             .path(publicApiCountryListPath)
                                             .queryParams(PublicApiUtils.createQueryParams())
                                             .build(true)
                                             .toUri();
        ResponseEntity<Map> responseEntity = publicApiRestTemplate.exchange(
                new RequestEntity(HttpMethod.GET, requestUrl), Map.class
        );
        if (!responseEntity.getStatusCode()
                           .is2xxSuccessful() || responseEntity.getBody() == null) {
            log.error("Failed to get countries. statusCode:" + responseEntity.getStatusCode());
            throw new CountryApiFailedException("Failed to get countries. statusCode:" + responseEntity.getStatusCode());
        }
        return this.resolve(responseEntity.getBody());
    }

    private List<CountryFetchValue> resolve(Map resultMap) {
        Map<String, Object> responseMap = (Map<String, Object>) resultMap.get("response");
        Map<String, Object> bodyMap = (Map<String, Object>) responseMap.get("body");
        Map<String, Object> itemsMap = (Map<String, Object>) bodyMap.get("items");
        List<Map<String, Object>> itemList = (List<Map<String, Object>>) itemsMap.get("item");
        return itemList.stream()
                       .map(this::toCountryFetchValue)
                       .collect(Collectors.toList());
    }

    private CountryFetchValue toCountryFetchValue(Map<String, Object> countryFetchMap) {
        if (countryFetchMap == null) {
            return null;
        }
        return new CountryFetchValueImpl(
                PublicApiUtils.get(countryFetchMap, CountryFetchValueImpl.FieldName.ID),
                PublicApiUtils.get(countryFetchMap, CountryFetchValueImpl.FieldName.BASIC),
                PublicApiUtils.get(countryFetchMap, CountryFetchValueImpl.FieldName.CONTINENT),
                PublicApiUtils.get(countryFetchMap, CountryFetchValueImpl.FieldName.NAME),
                PublicApiUtils.get(countryFetchMap, CountryFetchValueImpl.FieldName.ENGLISH_NAME),
                PublicApiUtils.get(countryFetchMap, CountryFetchValueImpl.FieldName.IMAGE_URL),
                PublicApiUtils.get(countryFetchMap, CountryFetchValueImpl.FieldName.WRITTEN_DATE)
        );
    }
}
