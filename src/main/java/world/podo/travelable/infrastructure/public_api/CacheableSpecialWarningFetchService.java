package world.podo.travelable.infrastructure.public_api;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import world.podo.travelable.domain.country.SpecialWarningFetchService;
import world.podo.travelable.domain.country.SpecialWarningFetchValue;

import java.util.List;
import java.util.Optional;

@Service(CacheableSpecialWarningFetchService.BEAN_NAME)
public class CacheableSpecialWarningFetchService implements SpecialWarningFetchService {
    public static final String BEAN_NAME = "cacheableSpecialWarningFetchService";

    private final SpecialWarningFetchService publicApiSpecialWarningFetchService;

    public CacheableSpecialWarningFetchService(
            @Qualifier(PublicApiSpecialWarningFetchService.BEAN_NAME) SpecialWarningFetchService publicApiSpecialWarningFetchService
    ) {
        this.publicApiSpecialWarningFetchService = publicApiSpecialWarningFetchService;
    }

    @Cacheable("SPECIAL_WARNING_LIST")
    @Override
    public List<SpecialWarningFetchValue> fetch() {
        return publicApiSpecialWarningFetchService.fetch();
    }

    @Cacheable("SPECIAL_WARNING_INFO")
    @Override
    public Optional<SpecialWarningFetchValue> fetchOne(String providerCountryId) {
        return publicApiSpecialWarningFetchService.fetchOne(providerCountryId);
    }
}
