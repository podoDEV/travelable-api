package world.podo.emergency.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import world.podo.emergency.domain.Member;
import world.podo.emergency.domain.MemberCountry;
import world.podo.emergency.ui.web.MemberDetailResponse;
import world.podo.emergency.ui.web.MemberSimpleResponse;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class MemberAssembler {
    private final CountryAssembler countryAssembler;

    public MemberSimpleResponse toMemberSimpleResponse(Member member) {
        if (member == null) {
            return null;
        }
        MemberSimpleResponse memberSimpleResponse = new MemberSimpleResponse();
        memberSimpleResponse.setId(member.getMemberId());
        memberSimpleResponse.setName(member.getName());
        return memberSimpleResponse;
    }

    public MemberDetailResponse toMemberDetailResponse(final Member member) {
        if (member == null) {
            return null;
        }
        MemberDetailResponse memberDetailResponse = new MemberDetailResponse();
        memberDetailResponse.setId(member.getMemberId());
        memberDetailResponse.setName(member.getName());
        memberDetailResponse.setCountrySimpleResponse(member.getMemberCountries()
                .stream()
                .map(MemberCountry::getCountry)
                .map(country -> countryAssembler.toCountrySimpleResponse(member, country))
                .collect(toList()));
        return memberDetailResponse;
    }
}
