package world.podo.emergency.infrastructure.firebase;

import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import world.podo.emergency.domain.PushException;
import world.podo.emergency.domain.PushRequest;
import world.podo.emergency.domain.PushService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FirebasePushService implements PushService {
    @Override
    public void send(PushRequest pushRequest) {
        Assert.notNull(pushRequest, "'pushRequest' must not be null");

        // This registration token comes from the client FCM SDKs.
        // See documentation on defining a message payload.
        List<Message> messages = pushRequest.getRegistrationTokens().stream()
                .map(token -> Message.builder()
                        .setNotification(new Notification(
                                "title", "body"
                        ))
                        .setToken(token)
                        .build())
                .collect(Collectors.toList());

        // Send a message to the device corresponding to the provided registration token.
        BatchResponse batchResponse = null;
        try {
            batchResponse = FirebaseMessaging.getInstance().sendAll(messages);
        } catch (FirebaseMessagingException ex) {
            log.error("Failed to send push message using firebase", ex);
            throw new PushException("Failed to send push message using firebase");
        }
        // Response is a message ID string.
        log.info("Successfully sent message: {}", batchResponse);
    }
}
