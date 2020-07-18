package world.podo.travelable.domain.member;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import world.podo.travelable.domain.country.Country;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 구독
 */
@Entity
@Builder
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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class MemberCountry {
    @EmbeddedId
    private MemberCountryId memberCountryId;
    /**
     * 구독 시작 시각
     */
    private LocalDateTime beginAt;
    /**
     * 구독 끝 시각
     */
    private LocalDateTime endAt;
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

    public MemberCountry update(
            LocalDateTime beginAt,
            LocalDateTime endAt,
            Boolean alarmEnabled
    ) {
        if (beginAt != null) {
            this.beginAt = beginAt;
        }
        if (endAt != null) {
            this.endAt = endAt;
        }
        if (alarmEnabled != null) {
            this.alarmEnabled = alarmEnabled;
        }
        return this;
    }
}
