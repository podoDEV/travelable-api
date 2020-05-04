package world.podo.emergency.infrastructure.public_api;

public class CountryApiFailedException extends PublicApiFailedException {
    private static final long serialVersionUID = 7744195102655402345L;

    public CountryApiFailedException(String message) {
        super(message);
    }
}
