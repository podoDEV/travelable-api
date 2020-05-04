package world.podo.emergency.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@RequiredArgsConstructor
public class ContactFactory {
    private final ContactRepository contactRepository;

    public Contact create(Country country) {
        Assert.notNull(country, "'country' must not be null");

        return new Contact(
                null,
                country,
                null,
                null,
                null,
                null,
                null
        );
    }
}
