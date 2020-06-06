package world.podo.emergency.ui.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SampleController {
    @GetMapping("sample")
    public ApiResponse<SampleResponse> getSamples() {
        return ApiResponse.data(SampleResponse.ghana());
    }
}
