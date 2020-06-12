package world.podo.emergency.ui.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import world.podo.emergency.application.CovidApplicationService;
import world.podo.emergency.domain.country.CovidFetchValue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class CovidController {
    private final CovidApplicationService covidApplicationService;

    @GetMapping("/covids")
    public ResponseEntity<ApiResponse> getCovid(
            @RequestParam String date
    ) {
        List<CovidFetchValue> covidFetchValues = covidApplicationService.getCovidValues(
                LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"))
        );
        return ResponseEntity.ok(
                ApiResponse.data(covidFetchValues)
        );
    }
}
