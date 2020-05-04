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
    /**
     * 첫번째 이미지 주소
     */
    private String firstImageUrl;
    /**
     * 두번째 이미지 주소
     */
    private String secondImageUrl;
    @CreatedDate
    private OffsetDateTime createdAt;
    @LastModifiedDate
    private OffsetDateTime updatedAt;

    public Contact update(
            String value,
            String firstImageUrl,
            String secondImageUrl
    ) {
        if (value != null) {
            this.value = value;
        }
        if (firstImageUrl != null) {
            this.firstImageUrl = firstImageUrl;
        }
        if (secondImageUrl != null) {
            this.secondImageUrl = secondImageUrl;
        }
        return this;
    }
}
