package world.podo.emergency.ui.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {
    @GetMapping
    public ResponseEntity<ApiResponse> getNotices(Pageable pageable) {
        // TODO: query notices
        return ResponseEntity.ok(ApiResponse.empty());
    }
}
