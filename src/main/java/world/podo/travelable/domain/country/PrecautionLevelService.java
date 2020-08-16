package world.podo.travelable.domain.country;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import world.podo.travelable.domain.DomainService;
import world.podo.travelable.infrastructure.public_api.CacheableSpecialWarningFetchService;
import world.podo.travelable.infrastructure.public_api.CacheableTravelBanFetchService;
import world.podo.travelable.infrastructure.public_api.CacheableWarningFetchService;

@Slf4j
@DomainService
public class PrecautionLevelService {
    private final WarningFetchService warningFetchService;
    private final TravelBanFetchService travelBanFetchService;
    private final SpecialWarningFetchService specialWarningFetchService;

    public PrecautionLevelService(
            @Qualifier(CacheableWarningFetchService.BEAN_NAME) WarningFetchService warningFetchService,
            @Qualifier(CacheableTravelBanFetchService.BEAN_NAME) TravelBanFetchService travelBanFetchService,
            @Qualifier(CacheableSpecialWarningFetchService.BEAN_NAME) SpecialWarningFetchService specialWarningFetchService) {
        this.warningFetchService = warningFetchService;
        this.travelBanFetchService = travelBanFetchService;
        this.specialWarningFetchService = specialWarningFetchService;
    }

    public PrecautionLevel getPrecautionLevel(String providerCountryId) {
        if (travelBanFetchService.fetchOne(providerCountryId)
                                 .isPresent()) {
            return PrecautionLevel.AVOID_ALL;
        }
        if (specialWarningFetchService.fetchOne(providerCountryId)
                                      .map(SpecialWarningFetchValue::isTravelAdvisory)
                                      .isPresent()) {
            return PrecautionLevel.AVOID_NONESSENTIAL;
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
