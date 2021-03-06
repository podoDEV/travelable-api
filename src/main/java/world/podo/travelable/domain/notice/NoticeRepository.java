package world.podo.travelable.domain.notice;

import org.springframework.data.jpa.repository.JpaRepository;
import world.podo.travelable.domain.country.Country;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Optional<Notice> findByProviderNoticeId(String providerNoticeId);

    List<Notice> findByCountryOrderByProviderNoticeId(Country country);
}
