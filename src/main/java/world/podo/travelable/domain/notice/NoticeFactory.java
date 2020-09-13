package world.podo.travelable.domain.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import world.podo.travelable.domain.PushContextHolder;
import world.podo.travelable.domain.country.Country;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
class NoticeFactory {
    private final NoticeRepository noticeRepository;
    private final PushContextHolder pushContextHolder;
    private final ApplicationEventPublisher applicationEventPublisher;

    Notice create(
            String providerNoticeId,
            String title,
            String textContent,
            String htmlContent,
            String writtenAt,
            Country country
    ) {
        Notice notice = new Notice(
                null,
                null,
                providerNoticeId,
                title,
                textContent,
                htmlContent,
                LocalDateTime.parse(writtenAt, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")),
                null,
                null
        );
        notice.setCountry(country);
        noticeRepository.saveAndFlush(notice);
        applicationEventPublisher.publishEvent(
                new NoticeCreatedEvent(
                        this,
                        notice,
                        notice.getCountry().getCountryId(),
                        pushContextHolder.get().doesSendPush()
                )
        );
        return notice;
    }
}
