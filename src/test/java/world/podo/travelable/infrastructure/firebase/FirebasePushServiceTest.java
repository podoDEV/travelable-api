package world.podo.travelable.infrastructure.firebase;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import world.podo.travelable.domain.PushRequest;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Ignore
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class FirebasePushServiceTest {
    @Autowired
    private FirebasePushService firebasePushService;

    @Test
    void send() {
        firebasePushService.send(
                PushRequest.builder()
                           .title("title")
                           .body("body")
                           .noticeId(1L)
                           .countryId(1L)
                           .registrationTokens(
                                   Stream.of(
                                           "fsZbZcO5TOmhPQMdWxT3MV:APA91bHrZGuoOinv8u1JckXxQ9iJykl4TE7qVeUroBipBsXGOCj7X8flIJFk-gsxl3J_1Ruu5tjiVmJFTyjH-xG4mlaRaPyq8dJ6dJm7RyxeARAuk0DQSAL6lkt0VufFKWKuq0ROZWS3"
                                   ).collect(Collectors.toSet())
                           )
                           .build()
        );
    }
}
