package world.podo.emergency.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import world.podo.emergency.domain.country.Country;
import world.podo.emergency.domain.country.CountryNotFoundException;
import world.podo.emergency.domain.country.CountryService;
import world.podo.emergency.domain.member.Member;
import world.podo.emergency.domain.member.MemberService;
import world.podo.emergency.ui.web.CountryDetailResponse;
import world.podo.emergency.ui.web.CountrySimpleResponse;

@Service
@Transactional
@RequiredArgsConstructor
public class CountryApplicationService {
    private final CountryService countryService;
    private final MemberService memberService;
    private final CountryAssembler countryAssembler;

    public Page<CountrySimpleResponse> getCountries(Long memberId, Boolean pinned, Pageable pageable) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(pageable, "'pageable' must not be null");

        Member member = memberService.getMember(memberId);
        return countryService.getCountries(memberId, pinned, pageable)
                             .map(country -> countryAssembler.toCountrySimpleResponse(member, country));
    }

    public CountryDetailResponse getCountry(Long memberId, Long countryId) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(countryId, "'countryId' must not be null");

        Member member = memberService.getMember(memberId);
        Country country = countryService.getCountry(countryId)
                                        .orElseThrow(CountryNotFoundException::new);
        return countryAssembler.toCountryDetailResponse(member, country);
    }
}
