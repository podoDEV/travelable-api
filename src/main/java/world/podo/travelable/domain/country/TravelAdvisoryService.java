package world.podo.travelable.domain.country;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import world.podo.travelable.infrastructure.public_api.PublicApiSpecialWarningFetchService;

import java.util.Objects;

@Service
public class TravelAdvisoryService {
    private final SpecialWarningFetchService specialWarningFetchService;

    public TravelAdvisoryService(
            @Qualifier(PublicApiSpecialWarningFetchService.BEAN_NAME) SpecialWarningFetchService specialWarningFetchService
    ) {
        this.specialWarningFetchService = specialWarningFetchService;
    }

    public boolean isTravelAdvisory(String providerCountryId) {
        Assert.notNull(providerCountryId, "'providerCountryId' must not be null");

        return specialWarningFetchService.fetch()
                                         .stream()
                                         .filter(SpecialWarningFetchValue::isTravelAdvisory)
                                         .map(SpecialWarningFetchValue::getId)
                                         .filter(Objects::nonNull)
                                         .anyMatch(it -> it.equals(providerCountryId));
    }
}
