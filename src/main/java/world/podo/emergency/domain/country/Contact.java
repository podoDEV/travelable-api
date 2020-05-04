package world.podo.emergency.domain.country;

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
     * 첫번째 이미지 주소
     */
    private String firstImageUrl;
    /**
     * 두번째 이미지 주소
     */
    private String secondImageUrl;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    Contact update(
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
