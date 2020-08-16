package world.podo.travelable.infrastructure.public_api;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import world.podo.travelable.domain.country.TravelBanFetchService;
import world.podo.travelable.domain.country.TravelBanFetchValue;

import java.util.List;

@ActiveProfiles("local")
@SpringBootTest
class PublicApiTravelBanFetchServiceTest {
    @Autowired
    @Qualifier(PublicApiTravelBanFetchService.BEAN_NAME)
    private TravelBanFetchService sut;

    @Ignore
    @Test
    void fetchList() {
        List<TravelBanFetchValue> values = sut.fetchList();
        values.forEach(System.out::println);
    }

    /**
     * TravelBanFetchValueImpl(id=375, ban=여행금지, banPartial=null, banNote=2014.8.4 ~ 2021.1.31)
     * TravelBanFetchValueImpl(id=131, ban=여행금지, banPartial=null, banNote=2011.8.20 ~ 2021.1.31)
     * TravelBanFetchValueImpl(id=120, ban=여행금지, banPartial=null, banNote=2007.8.7 ~ 2021.1.31)
     * TravelBanFetchValueImpl(id=174, ban=여행금지, banPartial=null, banNote=2007.8.7 ~ 2021.1.31)
     * TravelBanFetchValueImpl(id=284, ban=여행금지, banPartial=null, banNote=2007.8.7 ~ 2021.1.31)
     * TravelBanFetchValueImpl(id=294, ban=여행금지, banPartial=null, banNote=2011.6.28 ~ 2021.1.31)
     * TravelBanFetchValueImpl(id=252, ban=null, banPartial=여행금지(일부), banNote=민다나오의 잠보앙가, 술루‧바실란‧타위타위 군도(2015.12.1 ~ 2021.1.31))
     */
    @Ignore
    @Test
    void fetchOne() {
        TravelBanFetchValue value = sut.fetchOne("374")
                                       .orElse(null);
        System.out.println(value);
    }
}