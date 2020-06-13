package world.podo.emergency.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import world.podo.emergency.domain.country.Notice;
import world.podo.emergency.ui.web.NoticeResponse;

@Component
@RequiredArgsConstructor
class NoticeAssembler {

    NoticeResponse toNoticeResponse(Notice notice) {
        if (notice == null) {
            return null;
        }
        NoticeResponse noticeResponse = new NoticeResponse();
        noticeResponse.setId(notice.getNoticeId());
        noticeResponse.setTitle(notice.getTitle());
        noticeResponse.setTextContent(notice.getTextContent());
        noticeResponse.setHtmlContent(notice.getHtmlContent());
        noticeResponse.setCreatedAt(notice.getCreatedAt());
        return noticeResponse;
    }
}
