package world.podo.emergency.ui.web;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class NoticeResponse {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String title;
    private String textContent;
    private String htmlContent;
}
