package world.podo.emergency.ui.web;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CountryPinRequest {
    private LocalDateTime beginAt;
    private LocalDateTime endAt;
    private Boolean alarmEnabled = Boolean.TRUE;
}
