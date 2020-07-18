package world.podo.travelable.infrastructure.spring.security;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

@ToString
@EqualsAndHashCode(callSuper = false)
public class PodoAuthenticationToken extends AbstractAuthenticationToken implements MemberIdSupport<Long> {
    private static final long serialVersionUID = -2878659203022028269L;
    private final Long memberId;
    private final String accessToken;

    public PodoAuthenticationToken(Long memberId, String accessToken) {
        super(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        this.memberId = memberId;
        this.accessToken = accessToken;
    }

    @Override
    public String getCredentials() {
        return accessToken;
    }

    @Override
    public Long getPrincipal() {
        return memberId;
    }

    @Override
    public Long getMemberId() {
        return this.getPrincipal();
    }
}
