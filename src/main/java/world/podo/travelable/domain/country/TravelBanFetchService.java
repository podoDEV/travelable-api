package world.podo.travelable.domain.country;

import java.util.List;
import java.util.Optional;

public interface TravelBanFetchService {
    List<TravelBanFetchValue> fetchList();

    Optional<TravelBanFetchValue> fetchOne(String providerCountryId);
}
