package world.podo.emergency.domain;

import java.util.List;

public interface NoticeFetchService {
    List<NoticeFetchSimpleValue> fetchByCountryCode(String countryCode);

    NoticeFetchDetailValue fetchOne(String id);
}
