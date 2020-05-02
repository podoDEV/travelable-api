package world.podo.emergency.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import world.podo.emergency.domain.Member;
import world.podo.emergency.ui.web.MemberResponse;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class MemberAssembler {
    private final CountryAssembler countryAssembler;

    public MemberResponse toMemberResponse(final Member member) {
        if (member == null) {
            return null;
        }
        MemberResponse memberResponse = new MemberResponse();
        memberResponse.setId(member.getMemberId());
        memberResponse.setName(member.getName());
        memberResponse.setCountryResponses(member.getCountries()
                .stream()
                .map(country -> countryAssembler.toCountryResponse(member, country))
                .collect(toList()));
        return memberResponse;
    }
}
