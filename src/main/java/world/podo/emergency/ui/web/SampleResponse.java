package world.podo.emergency.ui.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
    private List<String> notices;

    public static SampleResponse ghana() {
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
                Collections.emptyList()
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
