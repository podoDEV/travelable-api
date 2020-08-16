package world.podo.travelable.domain.country;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import world.podo.travelable.infrastructure.public_api.PublicApiTravelBanFetchService;
import world.podo.travelable.infrastructure.public_api.PublicApiWarningFetchService;

@Slf4j
@Service
public class PrecautionLevelService {
    private final WarningFetchService warningFetchService;
    private final TravelBanFetchService travelBanFetchService;

    public PrecautionLevelService(
            @Qualifier(PublicApiWarningFetchService.BEAN_NAME) WarningFetchService warningFetchService,
            @Qualifier(PublicApiTravelBanFetchService.BEAN_NAME) TravelBanFetchService travelBanFetchService
    ) {
        this.warningFetchService = warningFetchService;
        this.travelBanFetchService = travelBanFetchService;
    }

    public PrecautionLevel getPrecautionLevel(String providerCountryId) {
        if (travelBanFetchService.fetchOne(providerCountryId)
                                 .isPresent()) {
            return PrecautionLevel.AVOID_ALL;
        }
        return warningFetchService.fetchOne(providerCountryId)
                                  .map(it -> {
                                      if (it.isUsual()) {
                                          return PrecautionLevel.USUAL;
                                      }
                                      if (it.isEnhanced()) {
                                          return PrecautionLevel.ENHANCED;
                                      }
                                      if (it.isAvoidNonessential()) {
                                          return PrecautionLevel.AVOID_NONESSENTIAL;
                                      }
                                      return PrecautionLevel.NONE;
                                  })
                                  .orElseGet(() -> {
                                      log.warn("Failed to get warningFetchValue. defaultValue: " + PrecautionLevel.NONE);
                                      return PrecautionLevel.NONE;
                                  });
    }

}
