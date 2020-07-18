package world.podo.travelable.ui.web;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank
    private String uuid;
    private String fcmToken;
}
