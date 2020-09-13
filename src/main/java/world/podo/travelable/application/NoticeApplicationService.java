package world.podo.travelable.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import world.podo.travelable.domain.country.CountryService;
import world.podo.travelable.domain.notice.NoticeFetchService;
import world.podo.travelable.domain.notice.NoticeFetchSimpleValue;
import world.podo.travelable.domain.notice.NoticeService;
import world.podo.travelable.domain.notice.NoticeSynchronizationService;
import world.podo.travelable.infrastructure.public_api.PublicApiCountryIsoCodeUtils;
import world.podo.travelable.ui.web.NoticeResponse;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeApplicationService {
    private final NoticeService noticeService;
    private final NoticeAssembler noticeAssembler;
    private final NoticeFetchService noticeFetchService;
    private final CountryService countryService;
    private final NoticeSynchronizationService noticeSynchronizationService;

    public Page<NoticeResponse> getNotices(Pageable pageable) {
        return noticeService.getNotices(pageable)
                            .map(noticeAssembler::toNoticeResponse);
    }

    public List<NoticeResponse> fetchAndSendPushMessage() {
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
                             .map(noticeAssembler::toNoticeResponse)
                             .collect(toList());
    }
}
