package world.podo.emergency.infrastructure.public_api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import world.podo.emergency.domain.CountryFetchService;
import world.podo.emergency.domain.CountryFetchValue;

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
                                             .queryParams(this.createQueryParams())
                                             .build(true)
                                             .toUri();
        ResponseEntity<Map> responseEntity = publicApiRestTemplate.exchange(
                new RequestEntity(HttpMethod.GET, requestUrl), Map.class
        );
        if (!responseEntity.getStatusCode()
                           .is2xxSuccessful() || responseEntity.getBody() == null) {
            log.error("Failed to get countries. statusCode:" + responseEntity.getStatusCode());
            throw new PublicApiFailedException("Failed to get countries. statusCode:" + responseEntity.getStatusCode());
        }
        return this.resolve(responseEntity.getBody());
    }

    private MultiValueMap<String, String> createQueryParams() {
        MultiValueMap<String, String> queryParameterMap = new LinkedMultiValueMap<>();
        queryParameterMap.set("_type", "json");
        queryParameterMap.set("pageNo", "1");
        queryParameterMap.set("numOfRows", "200");
        return queryParameterMap;
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
        return new CountryFetchValue(
                String.valueOf(countryFetchMap.get("basic")),
                String.valueOf(countryFetchMap.get("continent")),
                String.valueOf(countryFetchMap.get("countryName")),
                String.valueOf(countryFetchMap.get("countryEnName")),
                String.valueOf(countryFetchMap.get("id")),
                String.valueOf(countryFetchMap.get("imgUrl")),
                String.valueOf(countryFetchMap.get("wrtDt"))
        );
    }
}
