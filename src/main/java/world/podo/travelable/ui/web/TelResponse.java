package world.podo.travelable.ui.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TelResponse {
    @JsonProperty("names")
    private NamesResponse namesResponse;
    private String value;
}
