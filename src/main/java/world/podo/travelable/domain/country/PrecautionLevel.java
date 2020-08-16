package world.podo.travelable.domain.country;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum PrecautionLevel {
    NONE("none", "안전"),
    USUAL("usual", "practice usual precautions(여행 주의)"),
    ENHANCED("enhanced", "practice enhanced precautions(여행 자제)"),
    AVOID_NONESSENTIAL("avoidNonessential", "avoid non-essential precautions(철수 권고)"),
    AVOID_ALL("avoidAll", "avoid all travel(여행 금지)");

    private final String displayValue;
    private final String description;

    PrecautionLevel(String displayValue, String description) {
        this.displayValue = displayValue;
        this.description = description;
    }
}
