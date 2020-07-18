package world.podo.travelable.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.podo.travelable.domain.country.Country;
import world.podo.travelable.domain.country.CountryFetchService;
import world.podo.travelable.domain.country.CountrySynchronizationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CountrySynchronizationApplicationService {
    private final CountryFetchService countryFetchService;
    private final CountrySynchronizationService countrySynchronizationService;

    public List<Country> synchronizeCountries() {
        return countryFetchService.fetch()
                                  .stream()
                                  .map(countrySynchronizationService::synchronize)
                                  .collect(Collectors.toList());
    }
}
