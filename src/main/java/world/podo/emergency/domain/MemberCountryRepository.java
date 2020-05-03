package world.podo.emergency.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCountryRepository extends JpaRepository<MemberCountry, MemberCountryId> {
    Page<MemberCountry> findByMember_memberId(Long memberId, Pageable pageable);
}
