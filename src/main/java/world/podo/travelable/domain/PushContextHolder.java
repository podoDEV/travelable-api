package world.podo.travelable.domain;

import org.springframework.stereotype.Component;

@Component
public class PushContextHolder {
    private static final ThreadLocal<PushSendingType> CONTEXT_HOLDER = new ThreadLocal<>();

    public void initialize(boolean doesSendPushMessage) {
        CONTEXT_HOLDER.set(PushSendingType.from(doesSendPushMessage));
    }

    public PushSendingType get() {
        PushSendingType pushSendingType = CONTEXT_HOLDER.get();
        if (pushSendingType == null) {
            return PushSendingType.SEND_PUSH;
        }
        return pushSendingType;
    }
}
