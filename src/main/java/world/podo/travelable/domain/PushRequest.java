package world.podo.travelable.domain;

import lombok.*;

import java.util.Set;

@Builder
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PushRequest {
    private Set<String> registrationTokens;
    private Long countryId;
    private Long noticeId;
    private String title;
    private String body;
}
