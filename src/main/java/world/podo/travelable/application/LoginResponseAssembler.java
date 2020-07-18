package world.podo.travelable.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import world.podo.travelable.domain.member.Member;
import world.podo.travelable.ui.web.LoginResponse;

@Component
@RequiredArgsConstructor
public class LoginResponseAssembler {
    private final MemberAssembler memberAssembler;

    public LoginResponse toLoginResponse(String accessToken, Member member) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(accessToken);
        loginResponse.setMemberSimpleResponse(
                memberAssembler.toMemberSimpleResponse(member)
        );
        return loginResponse;
    }
}
