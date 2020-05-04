package world.podo.emergency.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.OffsetDateTime;
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
     * 대륙 정보
     */
    private String continent;
    /**
     * 기본 정보
     */
    @Column(length = 2000)
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
    private OffsetDateTime createdAt;
    @LastModifiedDate
    private OffsetDateTime updatedAt;
    @OneToMany(mappedBy = "country")
    private List<MemberCountry> memberCountries = new ArrayList<>();

    public Country update(
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
        if (continent != null) {
            this.continent = continent;
        }
        if (description != null) {
            this.description = description;
        }
        if (imageUrl != null) {
            this.imageUrl = imageUrl;
        }
        return this;
    }
}
