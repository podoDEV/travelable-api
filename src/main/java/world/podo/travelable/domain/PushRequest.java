package world.podo.travelable.domain;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Builder
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PushRequest {
    @NotEmpty
    private Set<String> registrationTokens;
    private Long countryId;
    private Long noticeId;
    @NotBlank
    private String title;
    @NotBlank
    private String body;
}
