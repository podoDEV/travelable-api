package world.podo.emergency.infrastructure.spring.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import world.podo.emergency.application.LoginResponseAssembler;
import world.podo.emergency.application.MemberApplicationService;
import world.podo.emergency.application.TokenApplicationService;
import world.podo.emergency.ui.web.ApiResponse;
import world.podo.emergency.ui.web.LoginResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JsonAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper objectMapper;
    private final TokenApplicationService<Long> tokenApplicationService;
    private final MemberApplicationService memberApplicationService;
    private final LoginResponseAssembler loginResponseAssembler;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        PodoAuthenticationToken podoAuthenticationToken = (PodoAuthenticationToken) authentication;
        Long memberId = podoAuthenticationToken.getMemberId();

        LoginResponse loginResponse = loginResponseAssembler.toLoginResponse(
                tokenApplicationService.encode(memberId),
                memberApplicationService.getMember(memberId)
        );

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(
                response.getOutputStream(),
                ApiResponse.data(loginResponse)
        );
    }
}
