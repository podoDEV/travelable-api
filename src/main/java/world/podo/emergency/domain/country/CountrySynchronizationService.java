package world.podo.emergency.domain.country;

import lombok.RequiredArgsConstructor;
import world.podo.emergency.domain.DomainService;

@DomainService
@RequiredArgsConstructor
public class CountrySynchronizationService {
    private final CountryService countryService;
    private final CountryFactory countryFactory;

    public Country synchronize(CountryFetchValue countryFetchValue) {
        return countryService.getCountryByProviderCountryId(countryFetchValue.getId())
                             .map(country -> country.update(
                                     countryFetchValue.getId(),
                                     countryFetchValue.getName(),
                                     countryFetchValue.getEnglishName(),
                                     countryFetchValue.getBasic(),
                                     countryFetchValue.getImageUrl()
                             ))
                             .orElseGet(() -> countryFactory.create(countryFetchValue));
    }
}
