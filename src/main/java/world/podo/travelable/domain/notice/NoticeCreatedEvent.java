package world.podo.travelable.domain.notice;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.context.ApplicationEvent;

@Value
@EqualsAndHashCode(callSuper = true)
public class NoticeCreatedEvent extends ApplicationEvent {
    Notice notice;
    Long countryId;
    boolean doesSendPushMessage;

    public NoticeCreatedEvent(Object source, Notice notice, Long countryId, boolean doesSendPushMessage) {
        super(source);
        this.notice = notice;
        this.countryId = countryId;
        this.doesSendPushMessage = doesSendPushMessage;
    }
}
