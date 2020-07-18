package world.podo.travelable.infrastructure.public_api;

public class CovidApiFailedException extends PublicApiFailedException {
    public CovidApiFailedException(String message) {
        super(message);
    }
}
