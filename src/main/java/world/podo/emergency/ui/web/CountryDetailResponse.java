package world.podo.emergency.ui.web;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class CountryDetailResponse {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
}
