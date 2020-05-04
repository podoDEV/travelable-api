package world.podo.emergency.ui.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import world.podo.emergency.application.CountrySynchronizationApplicationService;
import world.podo.emergency.domain.country.Country;

import java.util.List;

@Profile("batch")
@Slf4j
@Component
@RequiredArgsConstructor
public class ContactSynchronizationScheduler {
    private final CountrySynchronizationApplicationService countrySynchronizationApplicationService;

    @Scheduled(cron = "${cron.synchronization.country}")
    public void executeSynchronizingCountries() {
        List<Country> countries = countrySynchronizationApplicationService.synchronizeCountries();
        log.info("Countries are synchronized. size:{}", countries.size());
    }
}
