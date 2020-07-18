package world.podo.travelable.domain.country;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@RequiredArgsConstructor
class ContactFactory {
    private final ContactRepository contactRepository;

    Contact create(
            Country country,
            String value
    ) {
        Assert.notNull(country, "'country' must not be null");

        return contactRepository.save(
                Contact.builder()
                        .country(country)
                        .value(value)
                        .build()
        );
    }
}
