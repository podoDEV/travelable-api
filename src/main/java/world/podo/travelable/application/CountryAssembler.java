package world.podo.travelable.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import world.podo.travelable.domain.country.Contact;
import world.podo.travelable.domain.country.Country;
import world.podo.travelable.domain.country.CovidFetchValue;
import world.podo.travelable.domain.member.Member;
import world.podo.travelable.domain.member.MemberCountry;
import world.podo.travelable.domain.member.MemberCountryId;
import world.podo.travelable.domain.member.MemberCountryRepository;
import world.podo.travelable.domain.notice.Notice;
import world.podo.travelable.ui.web.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class CountryAssembler {
    private final MemberCountryRepository memberCountryRepository;
    private final NoticeAssembler noticeAssembler;
    private final CovidAssembler covidAssembler;

    CountrySimpleResponse toCountrySimpleResponse(Member member, Country country) {
        if (country == null) {
            return null;
        }
        CountrySimpleResponse countrySimpleResponse = new CountrySimpleResponse();
        countrySimpleResponse.setId(country.getCountryId());
        countrySimpleResponse.setName(country.getName());
        countrySimpleResponse.setPinned(memberCountryRepository.existsById(MemberCountryId.of(member, country)));
        return countrySimpleResponse;
    }

    CountryDetailResponse toCountryDetailResponse(Member member, Country country) {
        if (country == null) {
            return null;
        }
        CountryDetailResponse countryDetailResponse = new CountryDetailResponse();
        countryDetailResponse.setId(country.getCountryId());
        countryDetailResponse.setName(country.getName());
        countryDetailResponse.setPinned(memberCountryRepository.existsById(MemberCountryId.of(member, country)));
        countryDetailResponse.setContact(Optional.ofNullable(country.getContact())
                                                 .map(Contact::getValue)
                                                 .orElse(null));
        return countryDetailResponse;
    }

    CountryResponse toCountryResponse(Member member, Country country, List<Notice> notices, CovidFetchValue covidFetchValue) {
        Contact contact = country.getContact();

        CountryResponse countryResponse = new CountryResponse();
        countryResponse.setId(country.getCountryId());
        countryResponse.setNamesResponse(
                NamesResponse.builder()
                             .ko(country.getName())
                             .en(country.getEnglishName())
                             .build()
        );
//        countryResponse.setTelResponses(null);
//        countryResponse.setLink(null);
        countryResponse.setNotices(
                notices.stream()
                       .map(noticeAssembler::toNoticeResponse)
                       .collect(Collectors.toList())
        );
//        countryResponse.setPrecautionLevel();
//        countryResponse.setTravelAdvisory(false);
        if (contact != null) {
            countryResponse.setEmbassyResponse(
                    EmbassyResponse.builder()
                                   .address(contact.getEmbassyAddress())
                                   .email(contact.getEmbassyEmail())
                                   .description(contact.getEmbassyDescription())
                                   .emergencyNumber(contact.getEmbassyEmergencyNumber())
                                   .representativeNumber(contact.getEmbassyRepresentationNumber())
                                   .build()
            );
            if (contact.getCountryNumber() != null) {
                countryResponse.setCountryNumber(contact.getCountryNumber());
            }
            List<TelResponse> telResponses = new ArrayList<>();
            if (contact.getPoliceNumber() != null) {
                telResponses.add(TelResponse.police(contact.getPoliceNumber()));
            }
            if (contact.getFireStationNumber() != null) {
                telResponses.add(TelResponse.fireStation(contact.getFireStationNumber()));
            }
            if (contact.getAmbulanceNumber() != null) {
                telResponses.add(TelResponse.ambulance(contact.getAmbulanceNumber()));
            }
            if (!telResponses.isEmpty()) {
                countryResponse.setTelResponses(telResponses);
            }
        }
        countryResponse.setCovidResponse(
                covidAssembler.toCovidResponse(covidFetchValue)
        );

        MemberCountry memberCountry = memberCountryRepository.findByMemberAndCountry(member, country).orElse(null);
        if (memberCountry != null) {
            countryResponse.setPinned(true);
            countryResponse.setAlarmEnabled(memberCountry.getAlarmEnabled());
            countryResponse.setBeginAt(memberCountry.getBeginAt());
            countryResponse.setEndAt(memberCountry.getEndAt());
        } else {
            countryResponse.setPinned(false);
            countryResponse.setAlarmEnabled(null);
            countryResponse.setBeginAt(null);
            countryResponse.setEndAt(null);
        }
        return countryResponse;
    }
}
