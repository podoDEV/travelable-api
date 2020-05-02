package world.podo.emergency.ui.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.podo.emergency.application.LoginApplicationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class LoginController {
    private final LoginApplicationService loginApplicationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @RequestBody @Valid LoginRequest loginRequest
    ) {
        return ResponseEntity.ok(
                ApiResponse.data(
                        loginApplicationService.login(loginRequest.getUuid())
                )
        );
    }

    @PostMapping("/logout")
    public ResponseEntity logout() {
        // TODO: logout
        return ResponseEntity.noContent().build();
    }
}
