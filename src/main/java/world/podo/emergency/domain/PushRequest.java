package world.podo.emergency.domain;

import lombok.Data;

import java.util.Set;

@Data
public class PushRequest {
    private Set<String> registrationTokens;
}
