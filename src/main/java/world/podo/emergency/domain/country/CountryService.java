package world.podo.emergency.domain.country;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;
import world.podo.emergency.domain.DomainService;
import world.podo.emergency.domain.member.MemberCountry;
import world.podo.emergency.domain.member.MemberCountryRepository;

import java.util.List;
import java.util.Optional;

@DomainService
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;
    private final MemberCountryRepository memberCountryRepository;

    public Page<Country> getCountries(Long memberId, Boolean pinned, Pageable pageable) {
        if (pinned == null) {
            return countryRepository.findAll(pageable);
        }
        if (pinned) {
            return memberCountryRepository.findByMember_memberId(memberId, pageable)
                                          .map(MemberCountry::getCountry);
        } else {
            // TODO pinned false
            return Page.empty(pageable);
        }
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Optional<Country> getCountry(Long countryId) {
        return countryRepository.findById(countryId);
    }

    Optional<Country> getCountryByProviderCountryId(String providerCountryId) {
        Assert.notNull(providerCountryId, "'providerCountryId' must not be null");
        return countryRepository.findByProviderCountryId(providerCountryId);
    }
}
