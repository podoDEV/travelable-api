package world.podo.travelable.infrastructure.public_api;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.StringUtils;
import world.podo.travelable.domain.FieldNameSupport;
import world.podo.travelable.domain.country.SpecialWarningFetchValue;

@Data
@AllArgsConstructor
public class SpecialWarningFetchValueImpl implements SpecialWarningFetchValue {
    private String id;
    private String spLimit;
    private String spLimitPartial;
    private String spLimitNote;
    private String spBan;
    private String spBanPartial;
    private String spBanNote;

    @Override
    public boolean isTravelAdvisory() {
        return !StringUtils.isEmpty(spLimit) || !StringUtils.isEmpty(spLimitPartial);
    }

    public enum FieldName implements FieldNameSupport {
        ID("id", "인덱스"),
        SP_LIMIT("splimit", "특별여행주의"),
        SP_LIMIT_PARTIAL("splimitPartial", "특별여행주의 일부"),
        SP_LIMIT_NOTE("splimitNote", "특별여행주의 내용"),
        SP_BAN_YNA("spbanYna", "특별여행경보"),
        SP_BAN_PARTIAL("spbanYnPartial", "특별여행경보 일부"),
        SP_BAN_NOTE("spbanNote", "특별여행경보 내용");

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
