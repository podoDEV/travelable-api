package world.podo.emergency.domain.country;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@RequiredArgsConstructor
class ContactFactory {
    private final ContactRepository contactRepository;

    Contact create(
            Country country,
            String value,
            String firstImageUrl,
            String secondImageUrl
    ) {
        Assert.notNull(country, "'country' must not be null");

        return contactRepository.save(
                new Contact(
                        null,
                        country,
                        value,
                        firstImageUrl,
                        secondImageUrl,
                        null,
                        null
                )
        );
    }
}
