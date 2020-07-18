package world.podo.travelable.domain.country;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CountryFactory {
    private final CountryRepository countryRepository;

    Country create(CountryFetchValue countryFetchValue) {
        return countryRepository.save(
                Country.builder()
                        .name(countryFetchValue.getName())
                        .englishName(countryFetchValue.getEnglishName())
                        .description(countryFetchValue.getBasic())
                        .imageUrl(countryFetchValue.getImageUrl())
                        .providerCountryId(countryFetchValue.getId())
                        .build()
        );
    }
}
