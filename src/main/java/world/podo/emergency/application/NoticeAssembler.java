package world.podo.emergency.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import world.podo.emergency.domain.notice.Notice;
import world.podo.emergency.ui.web.NoticeResponse;

@Component
@RequiredArgsConstructor
class NoticeAssembler {

    NoticeResponse toNoticeResponse(Notice notice) {
        if (notice == null) {
            return null;
        }
        return new NoticeResponse(
                notice.getProviderNoticeId(),
                notice.getTitle(),
                notice.getTextContent(),
                notice.getHtmlContent(),
                notice.getCreatedAt()
        );
    }
}
