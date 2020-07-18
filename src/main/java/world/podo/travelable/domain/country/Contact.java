package world.podo.travelable.domain.country;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 연락처
 */
@Entity
@Builder
@Getter
@ToString
@EqualsAndHashCode(exclude = "country")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EntityListeners(AuditingEntityListener.class)
public class Contact {
    @Id
    @GeneratedValue
    private Long contactId;
    @OneToOne(mappedBy = "contact")
    private Country country;
    /**
     * 연락처 내용
     */
    @Lob
    private String value;
    /**
     * 전화 번호
     */
    private String policeNumber;
    private String fireStationNumber;
    private String ambulanceNumber;
    /**
     * 국가 번호
     */
    private String countryNumber;
    /**
     * 대사관 정보
     */
    private String embassyAddress;
    private String embassyEmail;
    private String embassyRepresentationNumber;
    private String embassyEmergencyNumber;
    private String embassyDescription;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    Contact update(
            String value
    ) {
        if (value != null) {
            this.value = value;
        }
        return this;
    }
}
