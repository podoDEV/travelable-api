package world.podo.travelable.ui.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.podo.travelable.application.ContactSynchronizationApplicationService;
import world.podo.travelable.application.CountrySynchronizationApplicationService;
import world.podo.travelable.application.NoticeSynchronizationApplicationService;

@RestController
@RequestMapping("/api/inner")
@RequiredArgsConstructor
public class FetchController {
    private final CountrySynchronizationApplicationService countrySynchronizationApplicationService;
    private final NoticeSynchronizationApplicationService noticeSynchronizationApplicationService;
    private final ContactSynchronizationApplicationService contactSynchronizationApplicationService;

    @PostMapping("/countries/sync")
    public void syncCountries() {
        countrySynchronizationApplicationService.synchronizeCountries();
    }

    @PostMapping("/notices/sync")
    public void syncNotices() {
        noticeSynchronizationApplicationService.synchronizeNotices();
    }

    @PostMapping("/contacts/sync")
    public void syncContacts() {
        contactSynchronizationApplicationService.synchronizeContacts();
    }
}
