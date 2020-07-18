package world.podo.travelable.ui.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TelResponse {
    @JsonProperty("names")
    private NamesResponse namesResponse;
    private String value;

    public static TelResponse ambulance(String ambulanceNumber) {
        return new TelResponse(
                NamesResponse.police(),
                ambulanceNumber
        );
    }

    public static TelResponse fireStation(String fireStationNumber) {
        return new TelResponse(
                NamesResponse.fireStation(),
                fireStationNumber
        );
    }

    public static TelResponse police(String policeNumber) {
        return new TelResponse(
                NamesResponse.police(),
                policeNumber
        );
    }
}
