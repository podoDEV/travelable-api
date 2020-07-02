package world.podo.emergency.infrastructure.public_api;

import lombok.AllArgsConstructor;
import lombok.Data;
import world.podo.emergency.domain.country.CovidFetchValue;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CovidFetchValueImpl implements CovidFetchValue {
    private LocalDateTime createdAt;
    private String countryName;
    private Integer totalDeathToll;
    private Integer totalConfirmCases;
    private Integer deltaConfirmCases;
}
