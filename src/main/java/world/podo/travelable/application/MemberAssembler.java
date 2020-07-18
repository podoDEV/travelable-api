package world.podo.travelable.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import world.podo.travelable.domain.member.Member;
import world.podo.travelable.domain.member.MemberCountry;
import world.podo.travelable.ui.web.MemberDetailResponse;
import world.podo.travelable.ui.web.MemberSimpleResponse;

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
