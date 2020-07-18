package world.podo.travelable.ui.web;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class CountrySimpleResponse {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String name;
    private boolean pinned;
}
