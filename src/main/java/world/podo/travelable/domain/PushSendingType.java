package world.podo.travelable.domain;

public enum PushSendingType {
    SEND_PUSH,
    DO_NOT_SEND_PUSH;

    public static PushSendingType from(Boolean aBoolean) {
        return aBoolean != null && aBoolean
                ? SEND_PUSH
                : DO_NOT_SEND_PUSH;
    }

    public boolean doesSendPush() {
        return this == SEND_PUSH;
    }
}
