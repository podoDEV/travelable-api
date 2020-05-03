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
public class Country {
    @Id
    @GeneratedValue
    private Long countryId;
    /**
     * 나라 이름
     */
    private String name;
    /**
     * 공공 API 에서 제공하는 아이디
     */
    private Long providerCountryId;
    @OneToOne
    private Contact contact;
    @CreatedDate
    private OffsetDateTime createdAt;
    @LastModifiedDate
    private OffsetDateTime updatedAt;
    @OneToMany(mappedBy = "country")
    private List<MemberCountry> memberCountries = new ArrayList<>();
}
