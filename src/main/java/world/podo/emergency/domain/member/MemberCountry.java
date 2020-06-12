package world.podo.emergency.domain.member;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import world.podo.emergency.domain.country.Country;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

/**
 * 구독
 */
@Entity
@Getter
@ToString(exclude = {
        "country",
        "member"
})
@EqualsAndHashCode(exclude = {
        "country",
        "member"
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class MemberCountry {
    @EmbeddedId
    private MemberCountryId memberCountryId;
    /**
     * 구독 시작 시각
     */
    private OffsetDateTime beginAt;
    /**
     * 구독 끝 시각
     */
    private OffsetDateTime endAt;
    /**
     * 알람 활성화 여부
     */
    private Boolean alarmEnabled;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @MapsId("countryId")
    @ManyToOne
    private Country country;

    @MapsId("memberId")
    @ManyToOne
    private Member member;
}
