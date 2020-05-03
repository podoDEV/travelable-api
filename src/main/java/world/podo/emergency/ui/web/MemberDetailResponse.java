package world.podo.emergency.ui.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

@Data
public class MemberDetailResponse {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String name;
    @JsonProperty("countries")
    private List<CountrySimpleResponse> countrySimpleResponse;
}
