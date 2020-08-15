package world.podo.travelable.domain.country;

/**
 * {
 * "continent": "중동/아프리카",
 * "countryEnName": "Ghana",
 * "countryName": "가나",
 * "id": 390,
 * "imgUrl": "http://www.0404.go.kr/imgsrc.mofa?atch_file_id=FILE_000000000010504&file_sn=1",
 * "imgUrl2": "http://www.0404.go.kr/imgsrc.mofa?atch_file_id=FILE_000000000010504&file_sn=3",
 * "isoCode": "GHA",
 * "splimit": "특별여행주의보",
 * "splimitNote": "전지역 전 지역",
 * "wrtDt": "2014-08-04"
 * },
 */
public interface SpecialWarningFetchValue {
    String getId();

    /**
     * 특별 여행 주의보 상태인지.
     */
    boolean isTravelAdvisory();
}
