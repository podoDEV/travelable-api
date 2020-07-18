package world.podo.travelable.ui.web;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
public class EmbassyResponse {
    private String address;
    private String email;
    private String representativeNumber;
    private String emergencyNumber;
    private String description;
}
