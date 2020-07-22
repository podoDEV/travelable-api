package world.podo.travelable.application;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.podo.travelable.domain.country.CovidFetchService;
import world.podo.travelable.domain.country.CovidFetchValue;
import world.podo.travelable.infrastructure.public_api.CacheableCovidFetchService;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class CovidApplicationService {
    private final CovidFetchService covidFetchService;

    public CovidApplicationService(
            @Qualifier(CacheableCovidFetchService.BEAN_NAME) CovidFetchService covidFetchService
    ) {
        this.covidFetchService = covidFetchService;
    }

    public List<CovidFetchValue> getCovidValues(LocalDate localDate) {
        return covidFetchService.fetch(localDate);
    }
}
