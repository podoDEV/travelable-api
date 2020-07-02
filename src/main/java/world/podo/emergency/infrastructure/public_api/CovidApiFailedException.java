package world.podo.emergency.infrastructure.public_api;

public class CovidApiFailedException extends PublicApiFailedException {
    public CovidApiFailedException(String message) {
        super(message);
    }
}
