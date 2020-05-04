package world.podo.emergency.domain.country;

import java.util.List;

public interface NoticeFetchService {
    List<NoticeFetchSimpleValue> fetchByCountryCode(String countryCode);

    NoticeFetchDetailValue fetchOne(String id);
}
