package world.podo.emergency.domain.country;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import world.podo.emergency.domain.DomainService;

@DomainService
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public Page<Notice> getNotices(Pageable pageable) {
        return noticeRepository.findAll(pageable);
    }
}
