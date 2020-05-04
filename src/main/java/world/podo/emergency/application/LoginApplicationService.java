package world.podo.emergency.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import world.podo.emergency.domain.member.Member;
import world.podo.emergency.domain.member.MemberService;

@Service
@RequiredArgsConstructor
public class LoginApplicationService {
    private final MemberService memberService;

    @Transactional
    public Member login(String uuid, String fcmToken) {
        Assert.hasText(uuid, "'uuid' must not be null, empty or blank");
        return memberService.getOrCreateMember(uuid, fcmToken);
    }
}
