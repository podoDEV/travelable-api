package world.podo.travelable.infrastructure.public_api;

public class PublicApiFailedException extends RuntimeException {
    private static final long serialVersionUID = 9108374228232747685L;

    public PublicApiFailedException(String message) {
        super(message);
    }
}
