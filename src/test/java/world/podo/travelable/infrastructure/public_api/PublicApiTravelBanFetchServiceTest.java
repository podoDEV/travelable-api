package world.podo.travelable.infrastructure.public_api;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import world.podo.travelable.domain.country.TravelBanFetchValue;

import java.util.List;

@ActiveProfiles("local")
@SpringBootTest
class PublicApiTravelBanFetchServiceTest {
    @Autowired
    private PublicApiTravelBanFetchService sut;

    @Ignore
    @Test
    void fetch() {
        List<TravelBanFetchValue> values = sut.fetch();
        values.forEach(System.out::println);
    }
}