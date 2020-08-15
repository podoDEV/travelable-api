package world.podo.travelable.infrastructure.public_api;

import lombok.AllArgsConstructor;
import lombok.Data;
import world.podo.travelable.domain.FieldNameSupport;
import world.podo.travelable.domain.country.WarningFetchValue;

/**
 * https://www.data.go.kr/data/15000827/openapi.do
 */
@Data
@AllArgsConstructor
public class WarningFetchValueImpl implements WarningFetchValue {

    private String id;
    private String attention;
    private String attentionPartial;
    private String attentionNote;
    private String control;
    private String controlPartial;
    private String controlNote;
    private String limita;
    private String limitaPartial;
    private String limitaNote;

    enum FieldName implements FieldNameSupport {
        ID("id", "인덱스"),
        ATTENTION("attention", "여행유의"),
        ATTENTION_PARTIAL("attention", "여행유의(일부)"),
        ATTENTION_NOTE("attention", "여행유의 내용"),
        CONTROL("control", "여행자제"),
        CONTROL_PARTIAL("controlPartial", "여행자제(일부)"),
        CONTROL_NOTE("controlNote", "여행자제 내용"),
        LIMITA("limita", "철수권고"),
        LIMITA_PARTIAL("limitaPartial", "철수권고(일부)"),
        LIMITA_NOTE("limitaNote", "철수권고 내용");

        private final String value;
        private final String description;

        FieldName(String value, String description) {
            this.value = value;
            this.description = description;
        }

        @Override
        public String getFieldName() {
            return null;
        }
    }
}
