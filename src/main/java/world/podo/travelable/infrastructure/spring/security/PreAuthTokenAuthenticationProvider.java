package world.podo.travelable.infrastructure.spring.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import world.podo.travelable.application.TokenApplicationService;

import java.util.Optional;

@RequiredArgsConstructor
public class PreAuthTokenAuthenticationProvider implements AuthenticationProvider {
    private final TokenApplicationService<Long> tokenApplicationService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        if (!this.supports(authentication.getClass())) {
            return null;
        }
        PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken = (PreAuthenticatedAuthenticationToken) authentication;
        Object principal = preAuthenticatedAuthenticationToken.getPrincipal();
        if (!(principal instanceof String)) {
            return null;
        }
        String accessToken = (String) principal;
        Optional<Long> memberIdOptional = tokenApplicationService.decode(accessToken);
        if (!memberIdOptional.isPresent()) {
            return null;
        }
        PodoAuthenticationToken podoAuthenticationToken = new PodoAuthenticationToken(
                memberIdOptional.get(),
                accessToken
        );
        podoAuthenticationToken.setAuthenticated(true);
        return podoAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
