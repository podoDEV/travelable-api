package world.podo.emergency.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import world.podo.emergency.domain.*;
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

        try {
            Member member = memberService.getMember(memberId);
            return countryService.getCountries(memberId, pinned, pageable)
                    .map(country -> countryAssembler.toCountrySimpleResponse(member, country));
        } catch (MemberNotFoundException ex) {
            throw new BadRequestException("Failed to get countries", ex);
        }
    }

    public CountryDetailResponse getCountry(Long memberId, Long countryId) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(countryId, "'countryId' must not be null");

        try {
            Member member = memberService.getMember(memberId);
            Country country = countryService.getCountry(countryId);
            return countryAssembler.toCountryDetailResponse(member, country);
        } catch (MemberNotFoundException | CountryNotFoundException ex) {
            throw new BadRequestException("Failed to get country", ex);
        }
    }
}
