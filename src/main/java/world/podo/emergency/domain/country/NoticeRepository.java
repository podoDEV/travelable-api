package world.podo.emergency.domain.country;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Optional<Notice> findByProviderNoticeId(String providerNoticeId);

    List<Notice> findByCountryOrderByProviderNoticeId(Country country);
}
