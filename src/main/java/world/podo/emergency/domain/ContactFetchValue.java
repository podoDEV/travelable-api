package world.podo.emergency.domain;

import lombok.Value;

/**
 * "contact": "<div>\r\n<h3 class=\"tit\">대사관 연락처</h3>ㅇ 주소 : No.10, Fifth Avenue Extension, Cantonments, P.O.Box GP 13700, Accra, Ghana<br>\r\nㅇ 대표번호(근무시간 중) : (233) 302-771-705,&nbsp;(233) 302-771-757, 776-157, 777-533<br>\r\nㅇ 긴급연락처(사건사고 등 긴급상황 발생 시, 24시간) : (233) 244-321-858<br>\r\nㅇ E mail : <a href=\"mailto:ghana@mofa.go.kr\">ghana@mofa.go.kr</a><br><br>\r\n\r\n<h3 class=\"tit\">주재국 신고</h3>\r\nㅇ 가나 국제전화 국가 식별번호 : 233<br>\r\n<p style=\"margin-left: 15px; margin-right: 15px;\">- 경찰 191 , 소방서 192/999, 앰블런스 193</p><br>\r\n\r\n[아크라]<br>\r\n<p style=\"margin-left: 15px; margin-right: 15px;\">- Ghana Police Service(경찰청) (233) 302-773-906, (toll free) 18555<br>\r\n- CID(범죄수사국) 본부 (233) 302-664-611, 665-561<br>\r\n- AIRPORT 경찰서 (233) 302-777-592<br>\r\n- Cantonment 경찰서 (233) 302-776-571</p><br>\r\n\r\n[테마]<br> \r\n<p style=\"margin-left: 15px; margin-right: 15px;\">- Regional (233) 303-202-259<br>\r\n- Main Harbour (233) 303-211-601<br>\r\n- Community(1) (233) 303-202-835<br>\r\n- Community(2) (233) 303-202-510</p><br>\r\n\r\n<h3 class=\"tit\">의료기관 연락처</h3>\r\nㅇ Greater Accra Regional Hospital(Ridge Hospital) (233) 302-228-315<br>\r\nㅇ Korle-Bu Teaching Hospital (233) 302-228-315<br>\r\nㅇ 37 Military Hospital (233) 302-228-315<br>\r\nㅇ Tema General Hospital (233) 302-228-315<br></div>",
 * "continent": "중동/아프리카",
 * "countryEnName": "Ghana",
 * "countryName": "가나",
 * "id": 390,
 * "imgUrl": "http://www.0404.go.kr/dev/fileDownload.mofa?atch_file_id=FILE_000000000010504&file_sn=1",
 * "imgUrl2": "http://www.0404.go.kr/dev/fileDownload.mofa?atch_file_id=FILE_000000000010504&file_sn=3",
 * "wrtDt": "2014-08-04"
 */
@Value
public class ContactFetchValue {
    private final String providerCountryId;
    private final String value;
    private final String firstImageUrl;
    private final String secondImageUrl;
    private final String writtenDate;
}
