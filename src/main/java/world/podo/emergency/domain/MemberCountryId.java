package world.podo.emergency.domain;

import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCountryId implements Serializable {
    private static final long serialVersionUID = 1729839338332735726L;

    private Long memberId;
    private Long countryId;

    public static MemberCountryId of(Member member, Country country) {
        Assert.notNull(member, "'member' must not be null");
        Assert.notNull(country, "'country' must not be null");
        return new MemberCountryId(
                member.getMemberId(),
                country.getCountryId()
        );
    }
}
