package world.podo.emergency.infrastructure.public_api;

public class ContactApiFailedException extends PublicApiFailedException {
    private static final long serialVersionUID = 6772996330753769893L;

    public ContactApiFailedException(String message) {
        super(message);
    }
}
