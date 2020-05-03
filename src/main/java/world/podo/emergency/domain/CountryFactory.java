package world.podo.emergency.domain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CountryFactory {
    public Country create(CountryFetchValue countryFetchValue) {
        return new Country(
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
        );
    }
}
