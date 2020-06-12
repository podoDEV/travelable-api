package world.podo.emergency.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.podo.emergency.domain.country.CovidFetchService;
import world.podo.emergency.ui.web.SampleResponse;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SampleApplicationService {
    private final CovidFetchService covidFetchService;

    public List<SampleResponse> getSampleResponses() {
        return Arrays.asList(
                SampleResponse.ghana(),
                SampleResponse.gabon(covidFetchService)
        );
    }
}
