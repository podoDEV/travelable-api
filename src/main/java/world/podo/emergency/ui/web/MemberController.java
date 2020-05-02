package world.podo.emergency.ui.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<MemberSimpleResponse>>> getMe() {
        // TODO: get my information
        return ResponseEntity.ok(
                ApiResponse.data(
                        Collections.singletonList(new MemberSimpleResponse())
                )
        );
    }
}
