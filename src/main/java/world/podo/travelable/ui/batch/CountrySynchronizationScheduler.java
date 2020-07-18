package world.podo.travelable.ui.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import world.podo.travelable.application.CountrySynchronizationApplicationService;
import world.podo.travelable.domain.country.Country;

import java.util.List;

@Profile("batch")
@Slf4j
@Component
@RequiredArgsConstructor
public class CountrySynchronizationScheduler {
    private final CountrySynchronizationApplicationService countrySynchronizationApplicationService;

    @Scheduled(cron = "${cron.synchronization.contact}")
    public void executeSynchronizingCountries() {
        List<Country> countries = countrySynchronizationApplicationService.synchronizeCountries();
        log.info("Contacts are synchronized. size:{}", countries.size());
    }
}
