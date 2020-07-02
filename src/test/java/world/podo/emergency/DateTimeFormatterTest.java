package world.podo.emergency;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatterTest {
    @Test
    public void parse() {
        LocalDateTime.parse(
                "2020-06-12T11:28:46.458+09:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        );
    }
}
