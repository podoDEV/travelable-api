package world.podo.emergency.domain.country;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class NoticeFactory {
    private final NoticeRepository noticeRepository;

    Notice create(
            String providerNoticeId,
            String title,
            String textContent,
            String htmlContent
    ) {
        return noticeRepository.save(
                new Notice(
                        null,
                        null,
                        providerNoticeId,
                        title,
                        textContent,
                        htmlContent,
                        null,
                        null
                )
        );
    }
}
