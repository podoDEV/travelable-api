package world.podo.travelable.infrastructure.public_api;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import world.podo.travelable.domain.country.SpecialWarningFetchService;
import world.podo.travelable.domain.country.SpecialWarningFetchValue;

import java.util.List;

@ActiveProfiles("local")
@SpringBootTest
class PublicApiSpecialWarningFetchServiceTest {
    @Autowired
    @Qualifier(PublicApiSpecialWarningFetchService.BEAN_NAME)
    private SpecialWarningFetchService sut;

    @Ignore
    @Test
    public void test() {
        List<SpecialWarningFetchValue> values = sut.fetch();
        values.forEach(System.out::println);
    }
}