package world.podo.emergency.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.podo.emergency.domain.country.*;
import world.podo.emergency.infrastructure.public_api.PublicApiCountryIsoCodeUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeSynchronizationApplicationService {

    private final CountryService countryService;
    private final NoticeFetchService noticeFetchService;
    private final NoticeSynchronizationService noticeSynchronizationService;

    public List<Notice> synchronizeNotices() {
        return countryService.getAllCountries()
                             .stream()
                             .map(Country::getName)
                             .map(PublicApiCountryIsoCodeUtils::getIsoCode)
                             .map(noticeFetchService::fetchByCountryCode)
                             .flatMap(Collection::stream)
                             .map(NoticeFetchSimpleValue::getId)
                             .distinct()
                             .map(noticeFetchService::fetchOne)
                             .map(noticeSynchronizationService::synchronize)
                             .filter(Objects::nonNull)
                             .collect(toList());
    }
}
