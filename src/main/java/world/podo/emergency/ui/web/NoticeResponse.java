package world.podo.emergency.ui.web;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NoticeResponse {
    private String id;
    private String title;
    private String textContent;
    private String htmlContent;
    private LocalDateTime createdAt;
}
