package world.podo.emergency.domain.country;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
class NoticeFactory {
    private final NoticeRepository noticeRepository;

    Notice create(
            String providerNoticeId,
            String title,
            String textContent,
            String htmlContent,
            String writtenAt
    ) {
        return noticeRepository.save(
                new Notice(
                        null,
                        null,
                        providerNoticeId,
                        title,
                        textContent,
                        htmlContent,
                        LocalDateTime.parse(writtenAt, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")),
                        null,
                        null
                )
        );
    }
}
