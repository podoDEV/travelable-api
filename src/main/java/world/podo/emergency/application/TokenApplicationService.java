package world.podo.emergency.application;

import java.util.Optional;

public interface TokenApplicationService<T> {
    String encode(T memberId);

    Optional<T> decode(String token);
}
