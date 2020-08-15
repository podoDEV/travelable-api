package world.podo.travelable.domain.country;

/**
 * {
 * "continent": "미주",
 * "countryEnName": "Colombia",
 * "countryName": "콜롬비아",
 * "id": 213,
 * "imgUrl": "http://www.0404.go.kr/imgsrc.mofa?atch_file_id=COUNTRY_213&file_sn=1",
 * "imgUrl2": "http://www.0404.go.kr/imgsrc.mofa?atch_file_id=COUNTRY_213&file_sn=3",
 * "isoCode": "COL",
 * "limitaNote": "베네수엘라 국경지역 20km, 뚜마코 시, 바예델카우카주(깔리 시 제외), 아라우카주, 카우카주, 안티오키아주 일부, 초코주 일부, 카케타주 일부  ",
 * "limitaPartial": "철수권고(일부)",
 * "wrtDt": "2011-09-19"
 * },
 */
public interface WarningFetchValue {
    String getId();

    String getLimitaNote();

    String getLimitaPartial();
}
