package world.podo.travelable.ui.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import world.podo.travelable.application.NoticeApplicationService;
import world.podo.travelable.domain.PushContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeApplicationService noticeApplicationService;
    private final PushContextHolder pushContextHolder;

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

    @PostMapping("/fetch-and-send-push-message")
    public ResponseEntity<ApiResponse<List<NoticeResponse>>> fetchAndSendPushMessage(
            @RequestHeader("Authorization") String authorization,
            HttpServletRequest request,
            Pageable pageable
    ) {
        this.initPushContext(request);
        return ResponseEntity.ok(
                ApiResponse.data(
                        noticeApplicationService.getNotices(pageable)
                                                .getContent()
                )
        );
    }

    private void initPushContext(HttpServletRequest request) {
        String header = request.getHeader("X-TRAVELABLE-PUSH-ENABLED");
        pushContextHolder.initialize(header != null);
    }
}
