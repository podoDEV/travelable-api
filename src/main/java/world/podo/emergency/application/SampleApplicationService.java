package world.podo.emergency.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.podo.emergency.ui.web.SampleResponse;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class SampleApplicationService {

    public List<SampleResponse> getSampleResponses() {
        return Collections.singletonList(
                SampleResponse.ghana()
        );
    }
}
