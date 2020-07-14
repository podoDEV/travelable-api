package world.podo.emergency.ui.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import world.podo.emergency.application.NoticeSynchronizationApplicationService;
import world.podo.emergency.domain.notice.Notice;

import java.util.List;

@Profile("batch")
@Slf4j
@Component
@RequiredArgsConstructor
public class NoticeSynchronizationScheduler {
    private final NoticeSynchronizationApplicationService noticeSynchronizationApplicationService;

    @Scheduled(cron = "${cron.synchronization.notice}")
    public void executeSynchronizingCountries() {
        List<Notice> notices = noticeSynchronizationApplicationService.synchronizeNotices();
        log.info("Notices are synchronized. size:{}", notices.size());
    }
}
