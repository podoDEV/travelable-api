package world.podo.emergency.domain.notice;

import lombok.Value;
import world.podo.emergency.domain.FieldNameSupport;

/**
 * "cnt": 24565,
 * "ctntHtml": "<p style=\"line-height: 1.5;\">...",
 * "ctntText": "전 중국지역에 여행경보 2단계(여행자제)...",
 * "fileUrl": "http://0404.go.kr/imgsrc.mofa?atch_file_id=FILE_000000000020973&file_sn=0",
 * "id": "ATC0000000007598    ",
 * "title": "전 중국지역에 여행경보 2단계(여행자제) 발령",
 * "wrtDt": "2020-01-28T11:49:41.461+09:00"
 */
@Value
public class NoticeFetchDetailValue {
    private final String id;
    private final String title;
    private final String textContent;
    private final String htmlContent;
    private final String fileUrl;
    private final String count;
    private final String writtenDate;

    public enum FieldName implements FieldNameSupport {
        ID("id"),
        TITLE("title"),
        TEXT_CONTENT("ctntText"),
        HTML_CONTENT("ctntHtml"),
        FILE_URL("fileUrl"),
        COUNT("cnt"),
        WRITTEN_DATE("wrtDt");

        private final String value;

        FieldName(String value) {
            this.value = value;
        }

        @Override
        public String getFieldName() {
            return value;
        }
    }
}
