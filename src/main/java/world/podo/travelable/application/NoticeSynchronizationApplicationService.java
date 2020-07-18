package world.podo.travelable.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import world.podo.travelable.domain.country.CountryService;
import world.podo.travelable.domain.notice.Notice;
import world.podo.travelable.domain.notice.NoticeFetchService;
import world.podo.travelable.domain.notice.NoticeFetchSimpleValue;
import world.podo.travelable.domain.notice.NoticeSynchronizationService;
import world.podo.travelable.infrastructure.public_api.PublicApiCountryIsoCodeUtils;

import java.util.List;

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
                             .flatMap(country -> {
                                 String name = country.getName();
                                 String countryIsoCode = PublicApiCountryIsoCodeUtils.getIsoCode(name);
                                 return noticeFetchService.fetchByCountryCode(countryIsoCode)
                                                          .stream()
                                                          .map(NoticeFetchSimpleValue::getId)
                                                          .filter(StringUtils::hasText)
                                                          .distinct()
                                                          .map(noticeFetchService::fetchOne)
                                                          .map(it -> noticeSynchronizationService.synchronize(country, it));
                             })
                             .collect(toList());
    }
}
