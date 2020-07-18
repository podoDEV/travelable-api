package world.podo.travelable.domain.country;

import java.time.LocalDate;
import java.util.List;

public interface CovidFetchService {
    List<CovidFetchValue> fetch(LocalDate localDate);
}
