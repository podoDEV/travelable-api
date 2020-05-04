package world.podo.emergency.ui.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.podo.emergency.application.NoticeApplicationService;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeApplicationService noticeApplicationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<NoticeResponse>>> getNotices(
            @RequestHeader("Authorization") String authorization,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                ApiResponse.data(
                        noticeApplicationService.getNotices(pageable)
                                                .getContent()
                )
        );
    }
}
