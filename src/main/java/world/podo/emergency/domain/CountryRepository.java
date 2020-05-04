package world.podo.emergency.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByProviderCountryId(String providerCountryId);
}
