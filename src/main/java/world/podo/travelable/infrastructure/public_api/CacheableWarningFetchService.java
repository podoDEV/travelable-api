package world.podo.travelable.infrastructure.public_api;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import world.podo.travelable.domain.country.WarningFetchService;
import world.podo.travelable.domain.country.WarningFetchValue;

import java.util.List;
import java.util.Optional;

@Service(CacheableWarningFetchService.BEAN_NAME)
public class CacheableWarningFetchService implements WarningFetchService {
    public static final String BEAN_NAME = "cacheableWarningFetchService";

    private final WarningFetchService publicApiWarningFetchService;

    public CacheableWarningFetchService(
            @Qualifier(PublicApiWarningFetchService.BEAN_NAME) WarningFetchService publicApiWarningFetchService
    ) {
        this.publicApiWarningFetchService = publicApiWarningFetchService;
    }

    @Cacheable("WARNING_LIST")
    @Override
    public List<WarningFetchValue> fetch() {
        return publicApiWarningFetchService.fetch();
    }

    @Cacheable("WARNING_INFO")
    @Override
    public Optional<WarningFetchValue> fetchOne(String providerCountryId) {
        return publicApiWarningFetchService.fetchOne(providerCountryId);
    }
}
