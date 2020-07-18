package world.podo.travelable.application;

import org.springframework.stereotype.Component;
import world.podo.travelable.domain.country.CovidFetchValue;
import world.podo.travelable.ui.web.CovidResponse;

@Component
public class CovidAssembler {

    public CovidResponse toCovidResponse(CovidFetchValue covidFetchValue) {
        if (covidFetchValue == null) {
            return null;
        }
        return new CovidResponse(
                covidFetchValue.getCreatedAt(),
                covidFetchValue.getTotalDeathToll(),
                covidFetchValue.getTotalConfirmCases(),
                covidFetchValue.getDeltaConfirmCases()
        );
    }
}
