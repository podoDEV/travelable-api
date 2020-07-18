package world.podo.travelable.application;

import java.util.Optional;

public interface TokenApplicationService<T> {
    String encode(T memberId);

    Optional<T> decode(String token);
}
