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
import world.podo.travelable.domain.country.ContactFetchService;
import world.podo.travelable.domain.country.ContactFetchValue;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PublicApiContactFetchService implements ContactFetchService {
    private final RestTemplate publicApiRestTemplate;
    private final String publicApiHost;
    private final String publicApiContactListPath;

    public PublicApiContactFetchService(
            @Qualifier("publicApiRestTemplate") RestTemplate publicApiRestTemplate,
            @Value("${public.api.host}") String publicApiHost,
            @Value("${public.api.path.contact-list}") String publicApiContactListPath) {
        this.publicApiRestTemplate = publicApiRestTemplate;
        this.publicApiHost = publicApiHost;
        this.publicApiContactListPath = publicApiContactListPath;
    }

    @Override
    public List<ContactFetchValue> fetch() {
        URI requestUrl = UriComponentsBuilder.fromHttpUrl(publicApiHost)
                                             .path(publicApiContactListPath)
                                             .queryParams(PublicApiUtils.createQueryParams())
                                             .build(true)
                                             .toUri();
        ResponseEntity<Map> responseEntity = publicApiRestTemplate.exchange(
                new RequestEntity<>(HttpMethod.GET, requestUrl), Map.class
        );
        if (!responseEntity.getStatusCode()
                           .is2xxSuccessful() || responseEntity.getBody() == null) {
            log.error("Failed to get contacts. statusCode:" + responseEntity.getStatusCode());
            throw new ContactApiFailedException("Failed to get contacts. statusCode:" + responseEntity.getStatusCode());
        }
        return this.resolve(responseEntity.getBody());
    }

    private List<ContactFetchValue> resolve(Map resultMap) {
        Map<String, Object> responseMap = (Map<String, Object>) resultMap.get("response");
        Map<String, Object> bodyMap = (Map<String, Object>) responseMap.get("body");
        Map<String, Object> itemsMap = (Map<String, Object>) bodyMap.get("items");
        List<Map<String, Object>> itemList = (List<Map<String, Object>>) itemsMap.get("item");
        return itemList.stream()
                       .map(this::toContactFetchValue)
                       .collect(Collectors.toList());
    }

    private ContactFetchValue toContactFetchValue(Map<String, Object> contactFetchMap) {
        if (contactFetchMap == null) {
            return null;
        }
        return new ContactFetchValueImpl(
                PublicApiUtils.get(contactFetchMap, ContactFetchValueImpl.FieldName.ID),
                PublicApiUtils.get(contactFetchMap, ContactFetchValueImpl.FieldName.VALUE),
                PublicApiUtils.get(contactFetchMap, ContactFetchValueImpl.FieldName.WRITTEN_DATE)
        );
    }
}
