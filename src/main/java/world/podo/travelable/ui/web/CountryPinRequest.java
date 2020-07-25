package world.podo.travelable.ui.web;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CountryPinRequest {
    private LocalDateTime beginAt = LocalDateTime.MIN;
    private LocalDateTime endAt = LocalDateTime.MAX;
    private Boolean alarmEnabled = Boolean.TRUE;
}
