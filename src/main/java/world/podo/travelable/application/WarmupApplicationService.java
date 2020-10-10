package world.podo.travelable.application;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import world.podo.travelable.domain.country.*;
import world.podo.travelable.infrastructure.public_api.CacheableCovidFetchService;
import world.podo.travelable.infrastructure.public_api.CacheableSpecialWarningFetchService;
import world.podo.travelable.infrastructure.public_api.CacheableTravelBanFetchService;
import world.podo.travelable.infrastructure.public_api.CacheableWarningFetchService;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class WarmupApplicationService {

    private final CountryService countryService;
    private final TravelBanFetchService travelBanFetchService;
    private final WarningFetchService warningFetchService;
    private final SpecialWarningFetchService specialWarningFetchService;
    private final CovidFetchService covidFetchService;

    public WarmupApplicationService(
            CountryService countryService,
            @Qualifier(CacheableTravelBanFetchService.BEAN_NAME) TravelBanFetchService travelBanFetchService,
            @Qualifier(CacheableWarningFetchService.BEAN_NAME) WarningFetchService warningFetchService,
            @Qualifier(CacheableSpecialWarningFetchService.BEAN_NAME) SpecialWarningFetchService specialWarningFetchService,
            @Qualifier(CacheableCovidFetchService.BEAN_NAME) CovidFetchService covidFetchService) {
        this.countryService = countryService;
        this.travelBanFetchService = travelBanFetchService;
        this.warningFetchService = warningFetchService;
        this.specialWarningFetchService = specialWarningFetchService;
        this.covidFetchService = covidFetchService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void warmup() {
        countryService.getAllCountries()
                      .stream()
                      .map(Country::getProviderCountryId)
                      .peek(travelBanFetchService::fetchOne)
                      .peek(warningFetchService::fetchOne)
                      .peek(specialWarningFetchService::fetchOne)
                      .collect(Collectors.toList());
        covidFetchService.fetch(LocalDate.now());
    }
}
