package world.podo.travelable.ui.web;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class CountryPinRequest {
    private LocalDateTime beginAt = LocalDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIN);
    private LocalDateTime endAt = LocalDateTime.of(LocalDate.of(2100, 12, 31), LocalTime.MAX);
    private Boolean alarmEnabled = Boolean.TRUE;
}
