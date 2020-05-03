package world.podo.emergency.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import world.podo.emergency.domain.*;
import world.podo.emergency.ui.web.CountryDetailResponse;
import world.podo.emergency.ui.web.CountrySimpleResponse;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CountryAssembler {
    private final MemberCountryRepository memberCountryRepository;

    public CountrySimpleResponse toCountrySimpleResponse(Member member, Country country) {
        if (country == null) {
            return null;
        }
        CountrySimpleResponse countrySimpleResponse = new CountrySimpleResponse();
        countrySimpleResponse.setId(country.getCountryId());
        countrySimpleResponse.setName(country.getName());
        countrySimpleResponse.setPinned(memberCountryRepository.existsById(MemberCountryId.of(member, country)));
        return countrySimpleResponse;
    }

    public CountryDetailResponse toCountryDetailResponse(Member member, Country country) {
        if (country == null) {
            return null;
        }
        CountryDetailResponse countryDetailResponse = new CountryDetailResponse();
        countryDetailResponse.setId(country.getCountryId());
        countryDetailResponse.setName(country.getName());
        countryDetailResponse.setPinned(memberCountryRepository.existsById(MemberCountryId.of(member, country)));
        countryDetailResponse.setContact(Optional.ofNullable(country.getContact())
                .map(Contact::getValue)
                .orElse(null));
        return countryDetailResponse;
    }
}
