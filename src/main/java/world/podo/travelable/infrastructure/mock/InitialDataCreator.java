package world.podo.travelable.infrastructure.mock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import world.podo.travelable.application.ContactSynchronizationApplicationService;
import world.podo.travelable.application.CountrySynchronizationApplicationService;
import world.podo.travelable.application.NoticeSynchronizationApplicationService;
import world.podo.travelable.domain.country.Contact;
import world.podo.travelable.domain.country.Country;
import world.podo.travelable.domain.notice.Notice;

import java.util.List;

@Profile("initialize")
@Slf4j
@Component
@RequiredArgsConstructor
public class InitialDataCreator {
    private final CountrySynchronizationApplicationService countrySynchronizationApplicationService;
    private final ContactSynchronizationApplicationService contactSynchronizationApplicationService;
    private final NoticeSynchronizationApplicationService noticeSynchronizationApplicationService;

    @EventListener(ApplicationReadyEvent.class)
    public void setUp() {
        try {
            List<Country> countries = countrySynchronizationApplicationService.synchronizeCountries();
            List<Contact> contacts = contactSynchronizationApplicationService.synchronizeContacts();
            List<Notice> notices = noticeSynchronizationApplicationService.synchronizeNotices();
            log.info("Initial data created. countries:{}, contacts:{}, notices:{}", countries.size(), contacts.size(), notices.size());
        } catch (Exception ex) {
            log.error("Failed to setup initial data", ex);
        }
    }
}
