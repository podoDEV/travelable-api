package world.podo.emergency.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 나라
 */
@Entity
@Getter
@ToString
@EqualsAndHashCode(exclude = "contact")
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
    private String description;
    /**
     * 기본 이미지 링크
     */
    private String imageUrl;
    /**
     * 공공 API 에서 제공하는 아이디
     */
    private String providerCountryId;
    @OneToOne
    private Contact contact;
    @CreatedDate
    private OffsetDateTime createdAt;
    @LastModifiedDate
    private OffsetDateTime updatedAt;
    @OneToMany(mappedBy = "country")
    private List<MemberCountry> memberCountries = new ArrayList<>();
}
