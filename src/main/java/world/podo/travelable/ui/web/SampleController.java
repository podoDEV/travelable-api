package world.podo.travelable.ui.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.podo.travelable.application.SampleApplicationService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SampleController {
    private final SampleApplicationService sampleApplicationService;

    @GetMapping("sample")
    public ApiResponse<List<CountryResponse>> getSamples() {
        return ApiResponse.data(
                sampleApplicationService.getSampleResponses()
        );
    }
}
