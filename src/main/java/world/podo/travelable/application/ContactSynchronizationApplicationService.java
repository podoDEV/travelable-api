package world.podo.travelable.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.podo.travelable.domain.country.Contact;
import world.podo.travelable.domain.country.ContactFetchService;
import world.podo.travelable.domain.country.ContactSynchronizationService;
import world.podo.travelable.domain.country.CountryNotFoundException;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ContactSynchronizationApplicationService {
    private final ContactFetchService contactFetchService;
    private final ContactSynchronizationService contactSynchronizationService;

    public List<Contact> synchronizeContacts() {
        return contactFetchService.fetch()
                                  .stream()
                                  .map(it -> {
                                      try {
                                          return contactSynchronizationService.synchronize(it);
                                      } catch (CountryNotFoundException ex) {
                                          log.warn("Country not found", ex);
                                          return null;
                                      }
                                  })
                                  .filter(Objects::nonNull)
                                  .collect(toList());
    }
}
