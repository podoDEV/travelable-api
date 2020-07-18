package world.podo.travelable.domain.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import world.podo.travelable.domain.country.Country;

import java.util.Optional;

public interface MemberCountryRepository extends JpaRepository<MemberCountry, MemberCountryId> {
    Page<MemberCountry> findByMember_memberId(Long memberId, Pageable pageable);

    Optional<MemberCountry> findByMemberAndCountry(Member member, Country country);
}
