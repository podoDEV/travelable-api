package world.podo.emergency.infrastructure.spring.security;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

@ToString
@EqualsAndHashCode(callSuper = false)
public class UuidAuthenticationToken extends AbstractAuthenticationToken {

    private final String uuid;
    private final String fcmToken;

    public UuidAuthenticationToken(String uuid, String fcmToken) {
        super(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        this.uuid = uuid;
        this.fcmToken = fcmToken;
    }

    public String getUuid() {
        return this.getPrincipal();
    }

    public String getFcmToken() {
        return fcmToken;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public String getPrincipal() {
        return uuid;
    }
}
