package world.podo.travelable.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import world.podo.travelable.domain.country.Country;
import world.podo.travelable.domain.country.CountryNotFoundException;
import world.podo.travelable.domain.country.CountryService;
import world.podo.travelable.domain.country.CovidFetchService;
import world.podo.travelable.domain.member.Member;
import world.podo.travelable.domain.member.MemberCountry;
import world.podo.travelable.domain.member.MemberCountryRepository;
import world.podo.travelable.domain.member.MemberService;
import world.podo.travelable.domain.notice.NoticeRepository;
import world.podo.travelable.ui.web.CountryDetailResponse;
import world.podo.travelable.ui.web.CountryPinRequest;
import world.podo.travelable.ui.web.CountryResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CountryApplicationService {
    private final CountryService countryService;
    private final MemberService memberService;
    private final MemberCountryRepository memberCountryRepository;
    private final CountryAssembler countryAssembler;
    private final NoticeRepository noticeRepository;
    private final CovidFetchService covidFetchService;

    public Page<CountryResponse> getCountries(Long memberId, Boolean pinned, Pageable pageable) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(pageable, "'pageable' must not be null");

        Member member = memberService.getMember(memberId);
        return countryService.getCountries(memberId, pinned, pageable)
                             .map(country -> countryAssembler.toCountryResponse(
                                     member,
                                     country,
                                     noticeRepository.findByCountryOrderByProviderNoticeId(country),
                                     covidFetchService.fetch(LocalDate.now()).stream()
                                                      .filter(it -> country.getName().equals(it.getCountryName()))
                                                      .findFirst()
                                                      .orElse(null)));
    }

    public CountryDetailResponse getCountry(Long memberId, Long countryId) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(countryId, "'countryId' must not be null");

        Member member = memberService.getMember(memberId);
        Country country = countryService.getCountry(countryId)
                                        .orElseThrow(CountryNotFoundException::new);
        return countryAssembler.toCountryDetailResponse(member, country);
    }

    public CountryDetailResponse pinCountry(
            Long memberId,
            Long countryId,
            CountryPinRequest countryPinRequest
    ) {
        Member member = memberService.getMember(memberId);
        Country country = countryService.getCountry(countryId)
                                        .orElseThrow(CountryNotFoundException::new);

        LocalDateTime beginAt = Optional.ofNullable(countryPinRequest).map(CountryPinRequest::getBeginAt).orElse(null);
        LocalDateTime endAt = Optional.ofNullable(countryPinRequest).map(CountryPinRequest::getEndAt).orElse(null);
        Boolean alarmEnabled = Optional.ofNullable(countryPinRequest).map(CountryPinRequest::getAlarmEnabled).orElse(null);

        MemberCountry memberCountry = memberCountryRepository.findByMemberAndCountry(member, country)
                                                             .map(it -> it.update(beginAt, endAt, alarmEnabled))
                                                             .orElseGet(() -> MemberCountry.builder()
                                                                                           .member(member)
                                                                                           .country(country)
                                                                                           .beginAt(beginAt)
                                                                                           .endAt(endAt)
                                                                                           .alarmEnabled(alarmEnabled)
                                                                                           .build()
                                                             );
        memberCountryRepository.save(memberCountry);
        return countryAssembler.toCountryDetailResponse(member, country);
    }

    public void unpinCountry(
            Long memberId,
            Long countryId
    ) {
        Member member = memberService.getMember(memberId);
        Country country = countryService.getCountry(countryId)
                                        .orElseThrow(CountryNotFoundException::new);
        memberCountryRepository.findByMemberAndCountry(member, country)
                               .ifPresent(memberCountryRepository::delete);
    }
}
