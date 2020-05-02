package world.podo.emergency.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import world.podo.emergency.domain.Country;
import world.podo.emergency.domain.Member;
import world.podo.emergency.domain.MemberCountryId;
import world.podo.emergency.domain.MemberCountryRepository;
import world.podo.emergency.ui.web.CountryResponse;

@Component
@RequiredArgsConstructor
public class CountryAssembler {
    private final MemberCountryRepository memberCountryRepository;

    public CountryResponse toCountryResponse(Member member, Country country) {
        if (country == null) {
            return null;
        }
        CountryResponse countryResponse = new CountryResponse();
        countryResponse.setId(country.getCountryId());
        countryResponse.setName(country.getName());
        countryResponse.setPinned(memberCountryRepository.existsById(MemberCountryId.of(member, country)));
        return countryResponse;
    }
}
