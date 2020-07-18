package world.podo.travelable.ui.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    @JsonProperty("member")
    private MemberSimpleResponse memberSimpleResponse;
}
