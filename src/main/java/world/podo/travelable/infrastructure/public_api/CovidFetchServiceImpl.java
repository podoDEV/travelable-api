package world.podo.travelable.infrastructure.public_api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import world.podo.travelable.domain.country.CovidFetchService;
import world.podo.travelable.domain.country.CovidFetchValue;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service(CovidFetchServiceImpl.BEAN_NAME)
public class CovidFetchServiceImpl implements CovidFetchService {
    public static final String BEAN_NAME = "covidFetchServiceImpl";

    private final RestTemplate publicApiRestTemplate;
    private final String publicApiHost;
    private final String publicApiCovidListPath;

    public CovidFetchServiceImpl(
            @Qualifier("publicApiRestTemplate") RestTemplate publicApiRestTemplate,
            @Value("${public.api.host.covid-list}") String publicApiHost,
            @Value("${public.api.path.covid-list}") String publicApiCovidListPath) {
        this.publicApiRestTemplate = publicApiRestTemplate;
        this.publicApiHost = publicApiHost;
        this.publicApiCovidListPath = publicApiCovidListPath;
    }

    @Override
    public List<CovidFetchValue> fetch(LocalDate localDate) {
        List<CovidFetchValue> currentValues = this.fetchOnly(localDate);
        if (currentValues.isEmpty()) {
            localDate = localDate.minusDays(1L);
            currentValues = this.fetchOnly(localDate);
        }
        List<CovidFetchValue> pastValues = this.fetchOnly(localDate.minusDays(1L));
        return this.merge(currentValues, pastValues);
    }

    private List<CovidFetchValue> fetchOnly(LocalDate localDate) {
        MultiValueMap<String, String> parameterMap = PublicApiUtils.createQueryParams();
        String formattedDate = localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        parameterMap.put("startCreateDt", Collections.singletonList(formattedDate));
        parameterMap.put("endCreateDt", Collections.singletonList(formattedDate));

        URI requestUrl = UriComponentsBuilder.fromHttpUrl(publicApiHost)
                                             .path(publicApiCovidListPath)
                                             .queryParams(parameterMap)
                                             .build(true)
                                             .toUri();
        ResponseEntity<Map> responseEntity = publicApiRestTemplate.exchange(
                new RequestEntity<>(HttpMethod.GET, requestUrl), Map.class
        );
        if (!responseEntity.getStatusCode()
                           .is2xxSuccessful() || responseEntity.getBody() == null) {
            log.error("Failed to get covid information. statusCode:" + responseEntity.getStatusCode());
            throw new ContactApiFailedException("Failed to get covid information. statusCode:" + responseEntity.getStatusCode());
        }
        return this.resolve(
                responseEntity.getBody()
        );
    }

    private List<CovidFetchValue> resolve(Map resultMap) {
        try {
            Map<String, Object> responseMap = (Map<String, Object>) resultMap.get("response");
            Map<String, Object> bodyMap = (Map<String, Object>) responseMap.get("body");
            Integer totalCount = (int) bodyMap.get("totalCount");
            if (totalCount == 0) {
                return Collections.emptyList();
            }
            Map<String, Object> itemsMap = (Map<String, Object>) bodyMap.get("items");
            List<Map<String, Object>> itemList = (List<Map<String, Object>>) itemsMap.get("item");
            return itemList.stream()
                           .map(this::toCovidFetchValue)
                           .collect(Collectors.toList());
        } catch (ClassCastException ex) {
            log.error("Failed to resolve CovidFetchValue. resultMap:{}", resultMap, ex);
            return Collections.emptyList();
        }
    }

    private CovidFetchValue toCovidFetchValue(Map<String, Object> covidFetchMap) {
        if (covidFetchMap == null) {
            return null;
        }
        return new CovidFetchValueImpl(
                LocalDateTime.parse(
                        this.parseDateTime(
                                PublicApiUtils.get(covidFetchMap, "createDt")
                        ),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                ),
                PublicApiUtils.get(covidFetchMap, "nationNm"),
                Integer.parseInt(PublicApiUtils.get(covidFetchMap, "natDeathCnt")),
                Integer.parseInt(PublicApiUtils.get(covidFetchMap, "natDefCnt")),
                null
        );
    }

    private String parseDateTime(String input) {
        Matcher matcher = Pattern.compile("(\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d).*").matcher(input);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return input;
    }

    private List<CovidFetchValue> merge(List<CovidFetchValue> currentValues, List<CovidFetchValue> pastValues) {
        List<CovidFetchValue> resultValues = new ArrayList<>();
        for (CovidFetchValue currentValue : currentValues) {
            CovidFetchValue pastValue = pastValues.stream()
                                                  .filter(it -> it.getCountryName().equals(currentValue.getCountryName()))
                                                  .findFirst()
                                                  .orElse(null);
            if (pastValue == null) {
                resultValues.add(
                        new CovidFetchValueImpl(
                                currentValue.getCreatedAt(),
                                currentValue.getCountryName(),
                                currentValue.getTotalDeathToll(),
                                currentValue.getTotalConfirmCases(),
                                null
                        )
                );
            } else {
                resultValues.add(
                        new CovidFetchValueImpl(
                                currentValue.getCreatedAt(),
                                currentValue.getCountryName(),
                                currentValue.getTotalDeathToll(),
                                currentValue.getTotalConfirmCases(),
                                currentValue.getTotalConfirmCases() - pastValue.getTotalConfirmCases()
                        )
                );
            }
        }
        return resultValues;
    }
}
