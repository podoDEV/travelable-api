package world.podo.travelable.ui.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TelResponse {
    private static final String KEY_AMBULANCE = "ambulance";
    private static final String KEY_FIRE_STATION = "fireStation";
    private static final String KEY_POLICE= "police";

    @JsonProperty("names")
    private NamesResponse namesResponse;
    private String key;
    private String value;

    public static TelResponse ambulance(String ambulanceNumber) {
        return new TelResponse(
                NamesResponse.ambulance(),
                KEY_AMBULANCE,
                ambulanceNumber
        );
    }

    public static TelResponse fireStation(String fireStationNumber) {
        return new TelResponse(
                NamesResponse.fireStation(),
                KEY_FIRE_STATION,
                fireStationNumber
        );
    }

    public static TelResponse police(String policeNumber) {
        return new TelResponse(
                NamesResponse.police(),
                KEY_POLICE,
                policeNumber
        );
    }
}
