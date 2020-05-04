package world.podo.emergency.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class CountryFactory {
    private final CountryRepository countryRepository;

    public Country create(CountryFetchValue countryFetchValue) {
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
