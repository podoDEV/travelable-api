package world.podo.travelable.domain.notice;

import java.util.List;

public interface NoticeFetchService {
    List<NoticeFetchSimpleValue> fetchByCountryCode(String countryCode);

    NoticeFetchDetailValue fetchOne(String id);
}
