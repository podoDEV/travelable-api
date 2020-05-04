package world.podo.emergency.domain.country;

import lombok.RequiredArgsConstructor;
import world.podo.emergency.domain.DomainService;

@DomainService
@RequiredArgsConstructor
public class ContactSynchronizationService {
    private final CountryService countryService;
    private final ContactFactory contactFactory;

    public Contact synchronize(ContactFetchValue contactFetchValue) {
        return countryService.getCountryByProviderCountryId(contactFetchValue.getId())
                             .map(country -> {
                                 if (country.getContact() == null) {
                                     contactFactory.create(country);
                                     return country;
                                 } else {
                                     return country.updateContact(
                                             contactFetchValue.getValue(),
                                             contactFetchValue.getFirstImageUrl(),
                                             contactFetchValue.getSecondImageUrl()
                                     );
                                 }
                             })
                             .orElseThrow(CountryNotFoundException::new)
                             .getContact();
    }
}
