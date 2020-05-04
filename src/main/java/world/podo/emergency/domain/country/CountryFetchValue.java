package world.podo.emergency.domain.country;

import lombok.Value;

/**
 * "basic": "<div>ㅇ 국명 : 가나공화국 (Republic of Ghana)<br>\r\nㅇ 수도 : 아크라(Accra) - 인구 227만명 (2018년)<br>\r\n<p style=\"margin-left: 20px; margin-right: 20px;\">※ The Greater Accra Metropolitan Area(GAMA) : 인구 약 400만명 (2018)</p>\r\nㅇ 인구 : 3,028만명 (2019년)<br>\r\nㅇ 면적 : 238,539㎢ (한반도의 1.1배)<br>\r\nㅇ 시간대 : GMT (우리나라보다 9시간 느림)<br>\r\nㅇ 언어 : 영어(공용어), 토착어(Twi어, Ewe어 등)<br>\r\nㅇ 종교 : 기독교(71%), 이슬람교(18.6%), 토속 종교(8.5%) 등</div>",
 * "continent": "중동/아프리카",
 * "countryEnName": "Ghana",
 * "countryName": "가나",
 * "id": 390,
 * "imgUrl": "http://www.0404.go.kr/imgsrc.mofa?atch_file_id=FILE_000000000010504&file_sn=1",
 * "wrtDt": "2014-08-04"
 */
@Value
public class CountryFetchValue {
    private final String id;
    private final String basic;
    private final String continent;
    private final String name;
    private final String englishName;
    private final String imageUrl;
    private final String writtenDate;
}
