package world.podo.emergency.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import world.podo.emergency.domain.member.Member;
import world.podo.emergency.ui.web.LoginResponse;

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
