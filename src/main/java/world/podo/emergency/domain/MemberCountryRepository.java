package world.podo.emergency.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCountryRepository extends JpaRepository<MemberCountry, MemberCountryId> {
}
