package world.podo.travelable.application;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.podo.travelable.domain.country.CovidFetchService;
import world.podo.travelable.domain.notice.NoticeFetchService;
import world.podo.travelable.infrastructure.public_api.CacheableCovidFetchService;
import world.podo.travelable.ui.web.CountryResponse;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class SampleApplicationService {
    private final CovidFetchService covidFetchService;
    private final NoticeFetchService noticeFetchService;

    public SampleApplicationService(
            @Qualifier(CacheableCovidFetchService.BEAN_NAME) CovidFetchService covidFetchService,
            NoticeFetchService noticeFetchService) {
        this.covidFetchService = covidFetchService;
        this.noticeFetchService = noticeFetchService;
    }

    public List<CountryResponse> getSampleResponses() {
        return Arrays.asList(
                CountryResponse.ghana(covidFetchService, noticeFetchService),
                CountryResponse.gabon(covidFetchService, noticeFetchService),
                CountryResponse.guyana(covidFetchService, noticeFetchService)
        );
    }
}
