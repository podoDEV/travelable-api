package world.podo.emergency.domain.country;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.OffsetDateTime;

/**
 * 공지사항
 */
@Entity
@Getter
@ToString
@EqualsAndHashCode(exclude = "country")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Notice {
    @Id
    @GeneratedValue
    private Long noticeId;
    @ManyToOne
    private Country country;
    private String providerNoticeId;
    private String title;
    private String textContent;
    private String htmlContent;
    @CreatedDate
    private OffsetDateTime createdAt;
    @LastModifiedDate
    private OffsetDateTime updatedAt;

    Notice update(
            String providerNoticeId,
            String title,
            String textContent,
            String htmlContent
    ) {
        if (!this.providerNoticeId.equals(providerNoticeId)) {
            return this;
        }
        if (title != null) {
            this.title = title;
        }
        if (textContent != null) {
            this.textContent = textContent;
        }
        if (htmlContent != null) {
            this.htmlContent = htmlContent;
        }
        return this;
    }
}
