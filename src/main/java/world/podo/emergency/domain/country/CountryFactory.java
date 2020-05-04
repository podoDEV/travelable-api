package world.podo.emergency.domain.country;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
class CountryFactory {
    private final CountryRepository countryRepository;

    Country create(CountryFetchValue countryFetchValue) {
        return countryRepository.save(
                new Country(
                        null,
                        countryFetchValue.getName(),
                        countryFetchValue.getEnglishName(),
                        countryFetchValue.getContinent(),
                        countryFetchValue.getBasic(),
                        countryFetchValue.getImageUrl(),
                        countryFetchValue.getId(),
                        null,
                        null,
                        null,
                        new ArrayList<>()
                ));
    }
}
