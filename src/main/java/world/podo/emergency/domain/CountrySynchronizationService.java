package world.podo.emergency.domain;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class CountrySynchronizationService {
    private final CountryService countryService;
    private final CountryFactory countryFactory;

    public Country synchronize(CountryFetchValue countryFetchValue) {
        return countryService.getCountryByCoutryProviderId(countryFetchValue.getId())
                             .map(country -> country.update(
                                     countryFetchValue.getId(),
                                     countryFetchValue.getName(),
                                     countryFetchValue.getEnglishName(),
                                     countryFetchValue.getContinent(),
                                     countryFetchValue.getBasic(),
                                     countryFetchValue.getImageUrl()
                             ))
                             .orElseGet(() -> countryFactory.create(countryFetchValue));
    }
}
