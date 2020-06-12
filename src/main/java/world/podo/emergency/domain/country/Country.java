package world.podo.emergency.domain.country;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;
import world.podo.emergency.domain.member.MemberCountry;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 나라
 */
@Entity
@Getter
@ToString(exclude = {
        "memberCountries"
})
@EqualsAndHashCode(exclude = {
        "contact",
        "memberCountries"
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EntityListeners(AuditingEntityListener.class)
public class Country {
    @Id
    @GeneratedValue
    private Long countryId;
    /**
     * 나라 이름 (한국어)
     */
    private String name;
    /**
     * 나라 이름 (영어)
     */
    private String englishName;
    /**
     * 기본 정보
     */
    @Lob
    private String description;
    /**
     * 기본 이미지 링크
     */
    private String imageUrl;
    /**
     * 공공 API 에서 제공하는 아이디
     */
    @Column(unique = true)
    private String providerCountryId;
    @OneToOne
    private Contact contact;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "country")
    private List<MemberCountry> memberCountries = new ArrayList<>();

    Country update(
            String providerCountryId,
            String name,
            String englishName,
            String continent,
            String description,
            String imageUrl
    ) {
        Assert.notNull(providerCountryId, "'providerCountryId' must not be null");
        if (!this.providerCountryId.equals(providerCountryId)) {
            throw new IllegalArgumentException("'providerCountryId' must be equal to Country's providerCountryId. pcId: " + this.providerCountryId + ", inputValue: " + providerCountryId);
        }
        if (name != null) {
            this.name = name;
        }
        if (englishName != null) {
            this.englishName = englishName;
        }
        if (description != null) {
            this.description = description;
        }
        if (imageUrl != null) {
            this.imageUrl = imageUrl;
        }
        return this;
    }

    void setContact(Contact contact) {
        if (this.contact != null) {
            return;
        }
        this.contact = contact;
    }

    Country updateContact(
            String value
    ) {
        if (contact != null) {
            contact.update(value);
        }
        return this;
    }
}
