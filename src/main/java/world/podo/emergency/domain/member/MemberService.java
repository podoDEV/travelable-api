package world.podo.emergency.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import world.podo.emergency.domain.DomainService;

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
        return memberRepository.findByUuid(uuid)
                               .orElseGet(() -> memberRepository.save(
                                       Member.of(uuid, fcmToken)
                               ));
    }

    public Member getMember(Long memberId) {
        Assert.notNull(memberId, "'memberId' must not be null");
        return memberRepository.findById(memberId)
                               .orElseThrow(MemberNotFoundException::new);
    }
}
