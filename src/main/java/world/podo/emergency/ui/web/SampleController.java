package world.podo.emergency.ui.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SampleController {
    @GetMapping("sample")
    public ApiResponse<List<SampleResponse>> getSamples() {
        return ApiResponse.data(
                Collections.singletonList(
                        SampleResponse.ghana()
                )
        );
    }
}
