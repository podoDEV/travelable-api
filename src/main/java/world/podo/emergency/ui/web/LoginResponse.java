package world.podo.emergency.ui.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginResponse {
    @JsonProperty("member")
    private MemberResponse memberResponse;
}
