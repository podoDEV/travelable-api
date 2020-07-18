package world.podo.emergency.ui.web;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class EmbassyResponse {
    private String address;
    private String email;
    private String representativeNumber;
    private String emergencyNumber;
    private String description;
}
