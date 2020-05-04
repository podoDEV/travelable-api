package world.podo.emergency.infrastructure.public_api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import world.podo.emergency.domain.NoticeFetchDetailValue;
import world.podo.emergency.domain.NoticeFetchService;
import world.podo.emergency.domain.NoticeFetchSimpleValue;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PublicApiNoticeFetchService implements NoticeFetchService {
    private final RestTemplate publicApiRestTemplate;
    private final String publicApiHost;
    private final String publicApiNoticeListPath;
    private final String publicApiNoticeOnePath;

    public PublicApiNoticeFetchService(
            @Qualifier("publicApiRestTemplate") RestTemplate publicApiRestTemplate,
            @Value("${public.api.host}") String publicApiHost,
            @Value("${public.api.path.notice-list}") String publicApiNoticeListPath,
            @Value("${public.api.path.notice-one}") String publicApiNoticeOnePath
    ) {
        this.publicApiRestTemplate = publicApiRestTemplate;
        this.publicApiHost = publicApiHost;
        this.publicApiNoticeListPath = publicApiNoticeListPath;
        this.publicApiNoticeOnePath = publicApiNoticeOnePath;
    }

    @Override
    public List<NoticeFetchSimpleValue> fetchByCountryCode(String countryCode) {
        URI requestUrl = UriComponentsBuilder.fromHttpUrl(publicApiHost)
                                             .path(publicApiNoticeListPath)
                                             .queryParams(PublicApiUtils.createQueryParams())
                                             .queryParam("isoCode1", countryCode)
                                             .build(true)
                                             .toUri();
        ResponseEntity<Map> responseEntity = publicApiRestTemplate.exchange(
                new RequestEntity(HttpMethod.GET, requestUrl), Map.class
        );
        if (!responseEntity.getStatusCode()
                           .is2xxSuccessful() || responseEntity.getBody() == null) {
            log.error("Failed to get notices. statusCode:" + responseEntity.getStatusCode());
            throw new CountryApiFailedException("Failed to get notices. statusCode:" + responseEntity.getStatusCode());
        }
        return this.resolveSimpleValue(responseEntity.getBody());
    }

    private List<NoticeFetchSimpleValue> resolveSimpleValue(Map resultMap) {
        Map<String, Object> responseMap = (Map<String, Object>) resultMap.get("response");
        Map<String, Object> bodyMap = (Map<String, Object>) responseMap.get("body");
        Map<String, Object> itemsMap = (Map<String, Object>) bodyMap.get("items");
        List<Map<String, Object>> itemList = (List<Map<String, Object>>) itemsMap.get("item");
        return itemList.stream()
                       .map(this::toNoticeFetchSimpleValue)
                       .collect(Collectors.toList());
    }

    private NoticeFetchSimpleValue toNoticeFetchSimpleValue(Map<String, Object> noticeFetchMap) {
        if (noticeFetchMap == null) {
            return null;
        }
        return new NoticeFetchSimpleValue(
                PublicApiUtils.get(noticeFetchMap, NoticeFetchSimpleValue.FieldName.ID),
                PublicApiUtils.get(noticeFetchMap, NoticeFetchSimpleValue.FieldName.FILE_URL),
                PublicApiUtils.get(noticeFetchMap, NoticeFetchSimpleValue.FieldName.TITLE),
                PublicApiUtils.get(noticeFetchMap, NoticeFetchSimpleValue.FieldName.WRITTEN_DATE)
        );
    }

    @Override
    public NoticeFetchDetailValue fetchOne(String id) {
        URI requestUrl = UriComponentsBuilder.fromHttpUrl(publicApiHost)
                                             .path(publicApiNoticeOnePath)
                                             .queryParam("id", id)
                                             .queryParam("_type", "json")
                                             .build(true)
                                             .toUri();
        ResponseEntity<Map> responseEntity = publicApiRestTemplate.exchange(
                new RequestEntity(HttpMethod.GET, requestUrl), Map.class
        );
        if (!responseEntity.getStatusCode()
                           .is2xxSuccessful() || responseEntity.getBody() == null) {
            log.error("Failed to get notice. statusCode:" + responseEntity.getStatusCode());
            throw new CountryApiFailedException("Failed to get notice. statusCode:" + responseEntity.getStatusCode());
        }
        return this.resolveDetailValue(responseEntity.getBody());
    }

    private NoticeFetchDetailValue resolveDetailValue(Map resultMap) {
        Map<String, Object> responseMap = (Map<String, Object>) resultMap.get("response");
        Map<String, Object> bodyMap = (Map<String, Object>) responseMap.get("body");
        Map<String, Object> itemsMap = (Map<String, Object>) bodyMap.get("items");
        Map<String, Object> item = (Map<String, Object>) itemsMap.get("item");
        return Optional.ofNullable(item)
                       .map(this::toNoticeFetchDetailValue)
                       .orElse(null);
    }

    private NoticeFetchDetailValue toNoticeFetchDetailValue(Map<String, Object> noticeFetchMap) {
        if (noticeFetchMap == null) {
            return null;
        }
        return new NoticeFetchDetailValue(
                PublicApiUtils.get(noticeFetchMap, NoticeFetchDetailValue.FieldName.ID),
                PublicApiUtils.get(noticeFetchMap, NoticeFetchDetailValue.FieldName.TITLE),
                PublicApiUtils.get(noticeFetchMap, NoticeFetchDetailValue.FieldName.TEXT_CONTENT),
                PublicApiUtils.get(noticeFetchMap, NoticeFetchDetailValue.FieldName.HTML_CONTENT),
                PublicApiUtils.get(noticeFetchMap, NoticeFetchDetailValue.FieldName.FILE_URL),
                PublicApiUtils.get(noticeFetchMap, NoticeFetchDetailValue.FieldName.COUNT),
                PublicApiUtils.get(noticeFetchMap, NoticeFetchDetailValue.FieldName.WRITTEN_DATE)
        );
    }
}
