package world.podo.emergency.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.OffsetDateTime;

/**
 * 연락처
 */
@Entity
@Getter
@ToString
@EqualsAndHashCode(exclude = "country")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contact {
    @Id
    @GeneratedValue
    private Long contactId;
    @OneToOne(mappedBy = "contact")
    private Country country;
    /**
     * 연락처 내용
     */
    private String value;
    @CreatedDate
    private OffsetDateTime createdAt;
    @LastModifiedDate
    private OffsetDateTime updatedAt;
}
