package world.podo.travelable.domain.notice;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import world.podo.travelable.domain.country.Country;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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
    @Setter
    private Country country;
    private String providerNoticeId;
    private String title;
    @Lob
    private String textContent;
    @Lob
    private String htmlContent;
    private LocalDateTime writtenAt;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    Notice update(
            String providerNoticeId,
            String title,
            String textContent,
            String htmlContent,
            String writtenAt,
            Country country
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
        if (writtenAt != null) {
            this.writtenAt = LocalDateTime.parse(
                    writtenAt,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            );
        }
        if (country != null) {
            this.country = country;
        }
        return this;
    }

    @PrePersist
    @PreUpdate
    public void postUpdate() {
        this.textContent = Optional.ofNullable(textContent)
                                   .map(it -> it.replaceAll("&nbsp;", ""))
                                   .orElse(null);
    }
}
