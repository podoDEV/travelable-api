package world.podo.emergency.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import world.podo.emergency.domain.member.Member;
import world.podo.emergency.domain.member.MemberNotFoundException;
import world.podo.emergency.domain.member.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberApplicationService {
    private final MemberRepository memberRepository;

    @Transactional
    public Member getMember(Long memberId) {
        Assert.notNull(memberId, "'memberId' must not be null");

        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }
}
