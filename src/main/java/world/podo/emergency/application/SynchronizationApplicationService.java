package world.podo.emergency.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import world.podo.emergency.domain.Country;
import world.podo.emergency.domain.CountryFetchService;
import world.podo.emergency.domain.CountrySynchronizationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SynchronizationApplicationService {
    private final CountryFetchService countryFetchService;
    private final CountrySynchronizationService countrySynchronizationService;

    public List<Country> synchronizeCountries() {
        return countryFetchService.fetch()
                           .stream()
                           .map(countrySynchronizationService::synchronize)
                           .collect(Collectors.toList());
    }
}
