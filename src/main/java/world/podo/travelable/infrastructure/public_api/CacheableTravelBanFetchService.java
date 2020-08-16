package world.podo.travelable.infrastructure.public_api;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import world.podo.travelable.domain.country.TravelBanFetchService;
import world.podo.travelable.domain.country.TravelBanFetchValue;

import java.util.List;
import java.util.Optional;

@Service(CacheableTravelBanFetchService.BEAN_NAME)
public class CacheableTravelBanFetchService implements TravelBanFetchService {
    public static final String BEAN_NAME = "cacheableTravelBanFetchService";

    private final TravelBanFetchService publicApiTravelBanFetchService;

    public CacheableTravelBanFetchService(
            @Qualifier(PublicApiTravelBanFetchService.BEAN_NAME) TravelBanFetchService publicApiTravelBanFetchService
    ) {
        this.publicApiTravelBanFetchService = publicApiTravelBanFetchService;
    }

    @Cacheable("TRAVEL_BAN_LIST")
    @Override
    public List<TravelBanFetchValue> fetchList() {
        return publicApiTravelBanFetchService.fetchList();
    }

    @Cacheable("TRAVEL_BAN_INFO")
    @Override
    public Optional<TravelBanFetchValue> fetchOne(String providerCountryId) {
        return publicApiTravelBanFetchService.fetchOne(providerCountryId);
    }
}
