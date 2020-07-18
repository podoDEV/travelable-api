package world.podo.travelable.infrastructure.spring.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import world.podo.travelable.application.LoginApplicationService;

@RequiredArgsConstructor
public class HttpBodyAuthenticationProvider implements AuthenticationProvider {
    private final LoginApplicationService loginApplicationService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        if (!this.supports(authentication.getClass())) {
            return null;
        }
        UuidAuthenticationToken token = (UuidAuthenticationToken) authentication;
        String uuid = token.getUuid();
        String fcmToken = token.getFcmToken();
        if (StringUtils.isEmpty(uuid)) {
            throw new BadCredentialsException("'uuid' must not be null or empty");
        }
        MemberIdContainer memberIdContainer = MemberIdContainer.from(
                loginApplicationService.login(uuid, fcmToken).getMemberId()
        );
        return new PodoAuthenticationToken(memberIdContainer.getMemberId(), uuid);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UuidAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

