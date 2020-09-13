package world.podo.travelable.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import world.podo.travelable.domain.PushContextHolder;
import world.podo.travelable.domain.PushRequest;
import world.podo.travelable.domain.PushService;
import world.podo.travelable.domain.country.CountryService;
import world.podo.travelable.domain.notice.NoticeCreatedEvent;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class NoticePushApplicationService {
    private final PushContextHolder pushContextHolder;
    private final PushService pushService;
    private final CountryService countryService;

    @TransactionalEventListener
    public void sendPushMessage(NoticeCreatedEvent noticeCreatedEvent) {
        if (!pushContextHolder.get().doesSendPush()) {
            return;
        }
        Long countryId = noticeCreatedEvent.getCountryId();
        Set<String> fcmTokens = countryService.getPinnedMembersTokens(countryId);
        pushService.send(PushRequest.builder()
                                    .title(noticeCreatedEvent.getNotice().getTitle())
                                    .body(noticeCreatedEvent.getNotice().getTextContent())
                                    .noticeId(noticeCreatedEvent.getNotice().getNoticeId())
                                    .countryId(countryId)
                                    .registrationTokens(fcmTokens)
                                    .build());
    }
}
