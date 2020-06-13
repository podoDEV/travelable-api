package world.podo.emergency.ui.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import world.podo.emergency.domain.country.*;
import world.podo.emergency.infrastructure.public_api.PublicApiCountryIsoCodeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * {
 * "id": "",
 * "names" : {
 * "ko": "가나",
 * "en": "Gana"
 * },
 * "countryNumber": "",
 * "tels": [
 * {
 * "key": "",
 * "value": ""
 * }, {
 * "key": "",
 * "value": ""
 * }
 * ],
 * "embassy": {
 * "address": "어딘가",
 * "email": "aaa@go.kr",
 * "representativeNumber": "문자열로 걍 줘",
 * "emergencyNumber": "",
 * "description": "",
 * },
 * "link": ""
 * "notices": [
 * ]
 * "precautionLevel": 1 ~ 4, // 여행 경보 단계 - 여행주의, 여행자제, 철수권고, 여행금지, 없으면 0
 * "travelAdvisory": true | false // 특별여행주의보
 * "covid": {
 * "totalDeathToll": 123, // 전체 사망자 수
 * "totalConfirmCases": 1000, // 전체 확진자 수
 * "deltaConfirmCases": -20, // 어제 오늘 확진자 수 차이
 * "updatedAt": "2020-06-12T12:34:56" // 한국시간, 실제 API 호출해서 받은 값으로 내려줌
 * },
 * "pinned": true | false,
 * "alarmEnabled": true | false,
 * "beginAt": "2020-06-12T10:00:00", // 한국시간. timezone 표시 안함
 * "endAt": "2020-06-13T15:00:00"
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SampleResponse {
    private Long id;
    @JsonProperty("names")
    private NamesResponse namesResponse;
    private String countryNumber;
    @JsonProperty("tels")
    private List<TelResponse> telResponses;
    @JsonProperty("embassy")
    private EmbassyResponse embassyResponse;
    private String link;
    private List<NoticeResponse> notices;
    private Integer precautionLevel;
    private Boolean travelAdvisory;
    @JsonProperty("covid")
    private CovidResponse covidResponse;
    private Boolean pinned;
    private Boolean alarmEnabled;
    private LocalDateTime beginAt;
    private LocalDateTime endAt;

    public static SampleResponse ghana(CovidFetchService covidFetchService, NoticeFetchService noticeFetchService) {
        CovidFetchValue covidFetchValue = covidFetchService.fetch(LocalDate.now()).stream()
                .filter(it -> "가나".equals(it.getCountryName()))
                .findFirst()
                .orElse(null);

        return new SampleResponse(
                ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE),
                new NamesResponse(
                        "가나",
                        "Ghana"
                ),
                "233",
                Arrays.asList(
                        new TelResponse(
                                new NamesResponse(
                                        "경찰",
                                        "Police"
                                ),
                                "191"
                        ),
                        new TelResponse(
                                new NamesResponse(
                                        "소방서",
                                        "Fire station"
                                ),
                                "192/999"
                        ),
                        new TelResponse(
                                new NamesResponse(
                                        "앰뷸런스",
                                        "Ambulance"
                                ),
                                "193"
                        )
                ),
                new EmbassyResponse(
                        "No.10, Fifth Avenue Extension, Cantonments, P.O.Box GP 13700, Accra, Ghana",
                        "ghana@mofa.go.kr",
                        "302-771-705, 302-771-757, 776-157, 777-533",
                        "244-321-858",
                        ""
                ),
                "http://0404.go.kr/dev/country_view.mofa?idx=390&hash=&chkvalue=no2&stext=&group_idx=&alert_level=0",
                noticeFetchService.fetchByCountryCode(
                        PublicApiCountryIsoCodeUtils.getIsoCode("가나")
                ).stream()
                        .map(NoticeFetchSimpleValue::getId)
                        .map(noticeFetchService::fetchOne)
                        .map(SampleResponse::toNoticeResponse)
                        .collect(Collectors.toList()),
                ThreadLocalRandom.current().nextInt(5),
                ThreadLocalRandom.current().nextBoolean(),
                toCovidResponse(covidFetchValue),
                ThreadLocalRandom.current().nextBoolean(),
                ThreadLocalRandom.current().nextBoolean(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public static SampleResponse gabon(CovidFetchService covidFetchService, NoticeFetchService noticeFetchService) {
        CovidFetchValue covidFetchValue = covidFetchService.fetch(LocalDate.now()).stream()
                .filter(it -> "가봉".equals(it.getCountryName()))
                .findFirst()
                .orElse(null);

        return new SampleResponse(
                ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE),
                new NamesResponse(
                        "가봉",
                        "Gabon"
                ),
                "241",
                Arrays.asList(
                        new TelResponse(
                                new NamesResponse(
                                        "경찰",
                                        "Police"
                                ),
                                "177"
                        )
                ),
                new EmbassyResponse(
                        "B.P. 2620 Libreville, GABON",
                        null,
                        "6530-1900",
                        "7750-7822",
                        null
                ),
                "http://0404.go.kr/dev/country_view.mofa?idx=2&hash=&chkvalue=no2&stext=&group_idx=&alert_level=0",
                noticeFetchService.fetchByCountryCode(
                        PublicApiCountryIsoCodeUtils.getIsoCode("가봉")
                ).stream()
                        .map(NoticeFetchSimpleValue::getId)
                        .map(noticeFetchService::fetchOne)
                        .map(SampleResponse::toNoticeResponse)
                        .collect(Collectors.toList()),
                ThreadLocalRandom.current().nextInt(5),
                ThreadLocalRandom.current().nextBoolean(),
                toCovidResponse(covidFetchValue),
                ThreadLocalRandom.current().nextBoolean(),
                ThreadLocalRandom.current().nextBoolean(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public static SampleResponse guyana(CovidFetchService covidFetchService, NoticeFetchService noticeFetchService) {
        CovidFetchValue covidFetchValue = covidFetchService.fetch(LocalDate.now()).stream()
                .filter(it -> "가이아나공화국".equals(it.getCountryName()))
                .findFirst()
                .orElse(null);

        return new SampleResponse(
                ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE),
                new NamesResponse(
                        "가이아나공화국",
                        "Guyana"
                ),
                "58",
                Arrays.asList(
                        new TelResponse(
                                new NamesResponse(
                                        "경찰",
                                        "Police"
                                ),
                                "911"
                        ),
                        new TelResponse(
                                new NamesResponse(
                                        "소방서",
                                        "Fire station"
                                ),
                                "912"
                        ),
                        new TelResponse(
                                new NamesResponse(
                                        "앰뷸런스",
                                        "Ambulance"
                                ),
                                "913"
                        )
                ),
                new EmbassyResponse(
                        "Av. Francisco de Miranda, Centro Lido, Torre B, Piso 9, Ofic.91-92-B, El Rosal, Caracas, Venezuela",
                        "venezuela@mofa.go.kr",
                        "(58) 212-954-1270",
                        "(58) 412-309-3247",
                        "※ 가이아나에는 우리대사관이 없으며, 주베네수엘라한국대사관이 겸임하고 있습니다."
                ),
                "http://0404.go.kr/dev/country_view.mofa?idx=315&hash=&chkvalue=no2&stext=&group_idx=&alert_level=0",
                noticeFetchService.fetchByCountryCode(
                        PublicApiCountryIsoCodeUtils.getIsoCode("가이아나공화국")
                ).stream()
                        .map(NoticeFetchSimpleValue::getId)
                        .map(noticeFetchService::fetchOne)
                        .map(SampleResponse::toNoticeResponse)
                        .collect(Collectors.toList()),
                ThreadLocalRandom.current().nextInt(5),
                ThreadLocalRandom.current().nextBoolean(),
                toCovidResponse(covidFetchValue),
                ThreadLocalRandom.current().nextBoolean(),
                ThreadLocalRandom.current().nextBoolean(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    private static CovidResponse toCovidResponse(CovidFetchValue covidFetchValue) {
        if (covidFetchValue == null) {
            return null;
        }
        return new CovidResponse(
                covidFetchValue.getCreatedAt(),
                covidFetchValue.getTotalDeathToll(),
                covidFetchValue.getTotalConfirmCases(),
                covidFetchValue.getDeltaConfirmCases()
        );
    }

    private static NoticeResponse toNoticeResponse(NoticeFetchDetailValue noticeFetchDetailValue) {
        if (noticeFetchDetailValue == null) {
            return null;
        }
        return new NoticeResponse(
                Optional.ofNullable(noticeFetchDetailValue.getId())
                        .map(String::trim)
                        .orElse(null),
                noticeFetchDetailValue.getTitle(),
                noticeFetchDetailValue.getTextContent(),
                noticeFetchDetailValue.getHtmlContent(),
                LocalDateTime.parse(
                        noticeFetchDetailValue.getWrittenDate(),
                        DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss.SZ")
                )
        );
    }

    @Data
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class NamesResponse {
        private String ko;
        private String en;
    }

    @Data
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class TelResponse {
        @JsonProperty("names")
        private NamesResponse namesResponse;
        private String value;
    }

    @Data
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class EmbassyResponse {
        private String address;
        private String email;
        private String representativeNumber;
        private String emergencyNumber;
        private String description;
    }
}
