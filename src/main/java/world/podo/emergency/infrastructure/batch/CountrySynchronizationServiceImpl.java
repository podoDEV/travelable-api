package world.podo.emergency.infrastructure.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import world.podo.emergency.domain.*;

@Component
@RequiredArgsConstructor
public class CountrySynchronizationServiceImpl implements CountrySynchronizationService {
    private final CountryService countryService;
    private final CountryFactory countryFactory;

    @Override
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
