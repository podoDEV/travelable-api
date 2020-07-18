package world.podo.travelable.ui.web;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class NamesResponse {
    private String ko;
    private String en;

    public static NamesResponse ambulance() {
        return NamesResponse.builder()
                            .ko("앰뷸런스")
                            .en("ambulance")
                            .build();
    }

    public static NamesResponse fireStation() {
        return NamesResponse.builder()
                            .ko("소방서")
                            .en("fireStation")
                            .build();
    }

    public static NamesResponse police() {
        return NamesResponse.builder()
                            .ko("경찰")
                            .en("police")
                            .build();
    }
}
