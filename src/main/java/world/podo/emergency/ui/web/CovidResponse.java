package world.podo.emergency.ui.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CovidResponse {
    private LocalDateTime createdAt;
    private Integer totalDeathToll;
    private Integer totalConfirmCases;
    private Integer deltaConfirmCases;
}
