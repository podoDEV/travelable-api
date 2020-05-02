package world.podo.emergency.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

@DomainService
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member getOrCreateMember(final String uuid) {
        Assert.hasText(uuid, "'uuid' must not be null, empty or blank");
        return memberRepository.findByUuid(uuid)
                .orElseGet(() -> memberRepository.save(
                        Member.from(uuid)
                ));
    }

    public Member getOrCreateMember(final String uuid, final String fcmToken) {
        Assert.hasText(uuid, "'uuid' must not be null, empty or blank");
        Assert.hasText(fcmToken, "'fcmToken' must not be null, empty or blank");

        return memberRepository.findByUuid(uuid)
                .orElseGet(() -> memberRepository.save(
                        Member.of(uuid, fcmToken)
                ));
    }
}
