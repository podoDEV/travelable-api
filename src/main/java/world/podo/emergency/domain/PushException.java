package world.podo.emergency.domain;

public class PushException extends RuntimeException {
    private static final long serialVersionUID = 7104783241895366256L;

    public PushException(String message) {
        super(message);
    }
}
