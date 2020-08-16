package world.podo.travelable.domain.country;

import java.util.List;
import java.util.Optional;

public interface WarningFetchService {
    List<WarningFetchValue> fetch();

    Optional<WarningFetchValue> fetchOne(String providerCountryId);
}
