package world.podo.travelable.ui.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.podo.travelable.domain.PushRequest;
import world.podo.travelable.domain.PushService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {
    private final PushService pushService;

    @PostMapping("/send-push-message")
    public ResponseEntity<?> sendPush(@RequestBody @Valid PushRequest pushRequest) {
        pushService.send(pushRequest);
        return ResponseEntity.noContent().build();
    }
}
