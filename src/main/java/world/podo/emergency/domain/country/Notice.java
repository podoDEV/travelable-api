package world.podo.emergency.domain.country;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 공지사항
 */
@Entity
@Getter
@ToString(exclude = {
        "country"
})
@EqualsAndHashCode(exclude = {
        "country",
        "textContent",
        "htmlContent"
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EntityListeners(AuditingEntityListener.class)
public class Notice {
    @Id
    @GeneratedValue
    private Long noticeId;
    @ManyToOne
    private Country country;
    private String providerNoticeId;
    private String title;
    @Lob
    private String textContent;
    @Lob
    private String htmlContent;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

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
