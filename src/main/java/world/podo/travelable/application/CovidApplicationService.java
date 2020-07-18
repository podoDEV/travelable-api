package world.podo.travelable.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.podo.travelable.domain.country.CovidFetchService;
import world.podo.travelable.domain.country.CovidFetchValue;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CovidApplicationService {
    private final CovidFetchService covidFetchService;

    public List<CovidFetchValue> getCovidValues(LocalDate localDate) {
        return covidFetchService.fetch(localDate);
    }
}
