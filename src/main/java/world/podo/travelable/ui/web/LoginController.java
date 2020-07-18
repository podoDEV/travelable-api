package world.podo.travelable.ui.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.podo.travelable.application.LoginApplicationService;

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
        // do nothing
        return null;
    }

    @PostMapping("/logout")
    public ResponseEntity logout() {
        // do nothing
        return null;
    }
}
