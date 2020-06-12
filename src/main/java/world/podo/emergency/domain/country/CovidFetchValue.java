package world.podo.emergency.domain.country;

import java.time.LocalDateTime;

public interface CovidFetchValue {
    String getCountryName();

    Integer getTotalDeathToll();

    Integer getTotalConfirmCases();

    Integer getDeltaConfirmCases();

    LocalDateTime getCreatedAt();
}
