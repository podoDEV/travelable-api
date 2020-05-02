package world.podo.emergency.ui.web;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {
    /**
     * 전체 나라 목록을 조회합니다
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<CountryDetailResponse>>> getCountries(
            @RequestParam(defaultValue = "false") Boolean pinned,
            Pageable pageable
    ) {
        // TODO: query countries
        return ResponseEntity.ok(
                ApiResponse.data(
                        Collections.singletonList(new CountryDetailResponse())
                )
        );
    }

    /**
     * 나라 1개를 조회합니다
     */
    @GetMapping("/{countryId}")
    public ResponseEntity<ApiResponse<CountryDetailResponse>> getCountries(
            @PathVariable Long countryId
    ) {
        // TODO: query country
        return ResponseEntity.ok(
                ApiResponse.data(
                        new CountryDetailResponse()
                )
        );
    }

    /**
     * 나라 1개를 구독합니다
     * 이미 구독되어있는 나라인 경우 성공으로 응답합니다
     */
    @PostMapping("/{countryId}/pin")
    public ResponseEntity<ApiResponse> pin(
            @PathVariable Long countryId
    ) {
        // TODO: pin
        return ResponseEntity.noContent().build();
    }

    /**
     * 나라 1개의 구독을 취소합니다
     * 이미 구독취소된 나라를 요청해도 성공으로 응답합니다
     */
    @PostMapping("/{countryId}/unpin")
    public ResponseEntity<ApiResponse> unpin(
            @PathVariable Long countryId
    ) {
        // TODO: unpin
        return ResponseEntity.noContent().build();
    }
}
