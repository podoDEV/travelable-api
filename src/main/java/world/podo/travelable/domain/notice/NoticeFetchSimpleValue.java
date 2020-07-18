package world.podo.travelable.domain.notice;

import lombok.Value;
import world.podo.travelable.domain.FieldNameSupport;

/**
 * "fileUrl": "http://0404.go.kr/imgsrc.mofa?atch_file_id=FILE_000000000020973&file_sn=0",
 * "id": "ATC0000000007598    ",
 * "title": "전 중국지역에 여행경보 2단계(여행자제) 발령",
 * "wrtDt": "2020-01-28T11:49:41.461+09:00"
 */
@Value
public class NoticeFetchSimpleValue {
    private final String id;
    private final String fileUrl;
    private final String title;
    private final String writtenDate;

    public enum FieldName implements FieldNameSupport {
        ID("id"),
        FILE_URL("fileUrl"),
        TITLE("title"),
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
