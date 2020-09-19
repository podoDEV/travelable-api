package world.podo.travelable.ui.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import world.podo.travelable.application.LoginApplicationService;
import world.podo.travelable.application.LoginResponseAssembler;
import world.podo.travelable.application.MemberApplicationService;
import world.podo.travelable.application.TokenApplicationService;
import world.podo.travelable.domain.PushRequest;
import world.podo.travelable.domain.PushService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest(controllers = {
        TestController.class
})
class TestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PushService pushService;

    // spring security
    @MockBean
    private TokenApplicationService tokenApplicationService;
    @MockBean
    private LoginApplicationService loginApplicationService;
    @MockBean
    private MemberApplicationService memberApplicationService;
    @MockBean
    private LoginResponseAssembler loginResponseAssembler;

    @Test
    void sendPush() throws Exception {
        mockMvc.perform(
                post("/api/test/send-push-message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsBytes(
                                        PushRequest.builder()
                                                   .build()
                                )
                        ))
               .andDo(print());
    }
}