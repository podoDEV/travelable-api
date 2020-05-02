package world.podo.emergency.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.OffsetDateTime;

/**
 * 구독
 */
@Entity
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCountry {
    @EmbeddedId
    private MemberCountryId memberCountryId;
    /**
     * 구독 시작 시각
     */
    private OffsetDateTime startedAt;
    /**
     * 구독 끝 시각
     */
    private OffsetDateTime endedAt;
    @CreatedDate
    private OffsetDateTime createdAt;
    @LastModifiedDate
    private OffsetDateTime updatedAt;

}
