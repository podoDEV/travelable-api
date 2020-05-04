package world.podo.emergency.ui.web;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {
    @GetMapping
    public ResponseEntity<ApiResponse> getNotices(Pageable pageable) {
        // TODO: query notices
        return ResponseEntity.ok(
                ApiResponse.data(
                        Collections.emptyList()
                )
        );
    }
}
