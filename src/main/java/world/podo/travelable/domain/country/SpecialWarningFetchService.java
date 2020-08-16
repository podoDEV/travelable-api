package world.podo.travelable.domain.country;

import java.util.List;
import java.util.Optional;

public interface SpecialWarningFetchService {
    List<SpecialWarningFetchValue> fetch();

    Optional<SpecialWarningFetchValue> fetchOne(String providerCountryId);
}
