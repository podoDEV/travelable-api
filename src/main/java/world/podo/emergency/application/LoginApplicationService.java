package world.podo.emergency.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import world.podo.emergency.domain.Member;
import world.podo.emergency.domain.MemberService;
import world.podo.emergency.ui.web.LoginResponse;

@Service
@RequiredArgsConstructor
public class LoginApplicationService {
    private final MemberService memberService;
    private final MemberAssembler memberAssembler;

    @Transactional
    public LoginResponse login(String uuid) {
        Assert.hasText(uuid, "'uuid' must not be null, empty or blank");
        Member member = memberService.getOrCreateMember(uuid);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMemberResponse(
                memberAssembler.toMemberResponse(member)
        );
        return loginResponse;
    }
}
