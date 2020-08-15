package world.podo.travelable.infrastructure.public_api;

import lombok.AllArgsConstructor;
import lombok.Data;
import world.podo.travelable.domain.FieldNameSupport;
import world.podo.travelable.domain.country.TravelBanFetchValue;

/**
 * https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15000504
 */
@Data
@AllArgsConstructor
public class TravelBanFetchValueImpl implements TravelBanFetchValue {
    private String id;
    private String ban;
    private String banPartial;
    private String banNote;

    enum FieldName implements FieldNameSupport {
        ID("id", "고유값(키값)"),
        BAN("ban", "여행금지"),
        BAN_PARTIAL("banPartial", "여행금지(일부)"),
        BAN_NOTE("banNote", "여행금지 내용");

        private final String value;
        private final String description;

        FieldName(String value, String description) {
            this.value = value;
            this.description = description;
        }

        @Override
        public String getFieldName() {
            return value;
        }
    }
}
